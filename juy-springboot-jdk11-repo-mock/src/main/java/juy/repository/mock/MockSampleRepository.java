package juy.repository.mock;

import juy.repository.SampleRepository;
import juy.repository.model.Sample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class MockSampleRepository implements SampleRepository {

    private final List<Sample> samples = new ArrayList<>();
    private AtomicInteger index = new AtomicInteger(0);

    @PostConstruct
    public void init()
    {
        for (int i = 0; i < 10; i++)
        {
            final Sample sample = new Sample();
            sample.setId(index.incrementAndGet());
            sample.setName("name_" + i);
            sample.setValue(UUID.randomUUID().toString());
            samples.add(sample);
        }
    }

    @Override
    public Sample save(Sample sample) {
        sample.setId(index.incrementAndGet());
        samples.add(sample);
        return sample;
    }

    @Override
    public boolean replace(Sample sample) {
        if (samples.remove(sample))
        {
            samples.add(sample);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Sample sample) {
        final int reference = samples.indexOf(sample);
        if (reference != -1)
        {
            final Sample existing = samples.get(reference);
            existing.setName(sample.getName());
            existing.setValue(sample.getValue());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        return samples.remove(id);
    }

    @Override
    public Sample findById(Integer id) {
        return samples.stream().filter( e -> id.equals(e.getId())).findFirst().get();
    }

    @Override
    public List<Sample> list() {
        return Collections.unmodifiableList(samples);
    }
}
