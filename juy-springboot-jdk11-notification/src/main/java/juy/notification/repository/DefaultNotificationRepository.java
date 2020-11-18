package juy.notification.repository;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import juy.notification.config.NotificationProperties;
import juy.notification.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Slf4j
@Repository
public class DefaultNotificationRepository implements NotificationRepository {

    @Autowired
    private NotificationProperties notificationProperties;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    public void init()
    {
        restTemplate.getInterceptors().add(new LoggingInterceptor());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    @Override
    public boolean send(Notification notification) {
        try {
            final String token = authenticate();
            log.info("token {}", token);

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "Bearer: " + token);

            final HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(notification), headers);
            final ResponseEntity<String> responseEntity = restTemplate.postForEntity(notificationProperties.getServer().getUrl(), request, String.class);
            final String body = responseEntity.getBody();
        } catch (IOException e)
        {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    public String authenticate() throws IOException
    {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN));

        final MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("grant_type", notificationProperties.getAuth().getGrantType());
        map.add("client_id", notificationProperties.getAuth().getClientId());
        map.add("client_secret", notificationProperties.getAuth().getClientSecret());

        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        final ResponseEntity<String> responseEntity = restTemplate.postForEntity(notificationProperties.getAuth().getUrl(), request, String.class);
        final String body = responseEntity.getBody();

        if (responseEntity.getStatusCode().is2xxSuccessful())
        {
            final JsonNode node = mapper.readTree(new StringReader(body));
            return node.get("access_token").asText();
        }
        return StringUtils.EMPTY;
    }
}

@Slf4j
class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest req, byte[] reqBody, ClientHttpRequestExecution ex) throws IOException {
        log.info("Request body: " +  new String(reqBody, StandardCharsets.UTF_8));
        log.info("Request headers: " +  req.getHeaders());

        final ClientHttpResponse response = ex.execute(req, reqBody);
        log.info("Response headers: " + response.getHeaders());
        return response;
    }
}