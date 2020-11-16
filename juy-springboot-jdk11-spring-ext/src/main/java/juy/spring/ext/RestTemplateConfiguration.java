package juy.spring.ext;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate createRestTemplate()
    {
        final RestTemplate restTemplate = new RestTemplate(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
        restTemplate.getInterceptors().add(new LoggingInterceptor());
        return restTemplate;
    }

}

@Slf4j
class LoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(
            HttpRequest req, byte[] reqBody, ClientHttpRequestExecution ex) throws IOException {
        log.info("Request body: " +  new String(reqBody, StandardCharsets.UTF_8));
        log.info("Request headers: " +  req.getHeaders());

        ClientHttpResponse response = ex.execute(req, reqBody);
        InputStreamReader isr = new InputStreamReader(
                response.getBody(), StandardCharsets.UTF_8);
        String body = new BufferedReader(isr).lines()
                .collect(Collectors.joining("\n"));
        log.info("Response body: " + body);
        log.info("Response headers: " + response.getHeaders());
        return response;
    }
}