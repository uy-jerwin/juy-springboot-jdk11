package juy.repository.api;

import juy.repository.SampleRepository;
import juy.repository.model.Sample;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApiSampleRepository implements SampleRepository {

    @Override
    public Sample save(Sample sample) {
        return null;
    }

    @Override
    public boolean replace(Sample sample) {
        return false;
    }

    @Override
    public boolean update(Sample sample) {
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Sample findById(Integer id) {
        return null;
    }

    @Override
    public List<Sample> list() {
        return null;
    }
}
