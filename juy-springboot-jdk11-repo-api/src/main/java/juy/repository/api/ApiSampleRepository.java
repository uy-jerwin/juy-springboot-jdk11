package juy.repository.api;

import juy.repository.SampleRepository;
import juy.repository.api.model.SampleEntity;
import juy.repository.model.Sample;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${repo.api.url}")
    private String url;

    @Autowired
    private RestTemplate restTemplate;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Sample save(Sample sample) {
        final SampleEntity sampleEntity = modelMapper.map(sample, SampleEntity.class);
        final ResponseEntity<SampleEntity> response = restTemplate.postForEntity(url, sampleEntity, SampleEntity.class);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return modelMapper.map(response.getBody(), Sample.class);
        }
        return null;
    }

    @Override
    public boolean replace(Sample sample) {
        final SampleEntity sampleEntity = modelMapper.map(sample, SampleEntity.class);
        final HttpEntity<SampleEntity> requestUpdate = new HttpEntity<>(sampleEntity);
        final ResponseEntity<SampleEntity> response =
                restTemplate.exchange(getUrl(sample.getId()), HttpMethod.PUT, requestUpdate, SampleEntity.class);
        return response.getStatusCode() == HttpStatus.OK;
    }

    @Override
    public boolean update(Sample sample) {
        final SampleEntity sampleEntity = modelMapper.map(sample, SampleEntity.class);
        final HttpEntity<SampleEntity> requestUpdate = new HttpEntity<>(sampleEntity);
        final ResponseEntity<SampleEntity> response =
                restTemplate.exchange(getUrl(sample.getId()), HttpMethod.PUT, requestUpdate, SampleEntity.class);
        return response.getStatusCode() == HttpStatus.OK;
    }

    @Override
    public boolean delete(Integer id) {
        restTemplate.delete(getUrl(id));
        return true;
    }

    @Override
    public Sample findById(Integer id) {
        final ResponseEntity<SampleEntity> response = restTemplate.getForEntity(getUrl(id), SampleEntity.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return modelMapper.map(response.getBody(), Sample.class);
        }
        return null;
    }

    @Override
    public List<Sample> list() {
        final ResponseEntity<SampleEntity[]> response = restTemplate.getForEntity(url, SampleEntity[].class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(modelMapper.map(response.getBody(), Sample[].class));
        }
        return null;
    }

    private String getUrl(int id) {
        return String.format("%s/%d", url, id);
    }
}
