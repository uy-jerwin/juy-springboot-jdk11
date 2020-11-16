package juy.repository.api;

import juy.repository.SampleRepository;
import juy.repository.api.config.WiremockProperties;
import juy.repository.model.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Repository
public class ApiSampleRepository implements SampleRepository {

    @Autowired
    private WiremockProperties wiremockProperties;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Sample save(Sample sample) {
        final ResponseEntity<Sample> response = restTemplate.postForEntity(getUrl(), sample, Sample.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return sample;
        }
        return null;
    }

    @Override
    public boolean replace(Sample sample) {
        final HttpEntity<Sample> requestUpdate = new HttpEntity(sample);
        final ResponseEntity<Sample> response =
                restTemplate.exchange(getUrl(sample.getId()), HttpMethod.PUT, requestUpdate, Sample.class);
        return response.getStatusCode() == HttpStatus.OK;
    }

    @Override
    public boolean update(Sample sample) {
        final HttpEntity<Sample> requestUpdate = new HttpEntity(sample);
        final ResponseEntity<Sample> response =
                restTemplate.exchange(getUrl(sample.getId()), HttpMethod.PUT, requestUpdate, Sample.class);
        return response.getStatusCode() == HttpStatus.OK;
    }

    @Override
    public boolean delete(Integer id) {
        restTemplate.delete(getUrl(id));
        return true;
    }

    @Override
    public Sample findById(Integer id) {
        final ResponseEntity<Sample> response = restTemplate.getForEntity(getUrl(id), Sample.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public List<Sample> list() {
        final ResponseEntity<Sample[]> response = restTemplate.getForEntity(getUrl(), Sample[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(response.getBody());
        }
        return null;
    }

    private String getUrl() {
        return String.format("http://%s:%s/samples", wiremockProperties.getHost(), wiremockProperties.getPort());
    }

    private String getUrl(int id) {
        return getUrl() + "/" + id;
    }
}
