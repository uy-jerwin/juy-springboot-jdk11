package juy.service;

import juy.repository.SampleRepository;
import juy.repository.model.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleService {

    @Autowired
    private SampleRepository sampleRepository;

    public Sample save(Sample sample) {
        return sampleRepository.save(sample);
    }

    public boolean replace(Sample sample) {
        return sampleRepository.replace(sample);
    }

    public boolean update(Sample sample) {
        return sampleRepository.update(sample);
    }

    public boolean delete(Integer id) {
        return sampleRepository.delete(id);
    }

    public Sample findById(Integer id) {
        return sampleRepository.findById(id);
    }

    public List<Sample> list() {
        return sampleRepository.list();
    }
}
