package juy.repository.db.repository;

import juy.repository.SampleRepository;
import juy.repository.db.model.SampleEntity;
import juy.repository.model.Sample;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class H2SampleRepository implements SampleRepository {

    @Autowired
    private SampleJpaRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public Sample save(Sample sample) {
        final SampleEntity entity = modelMapper.map(sample, SampleEntity.class);
        return modelMapper.map(repository.save(entity), Sample.class);
    }

    @Override
    public boolean replace(Sample sample) {
        final SampleEntity entity = modelMapper.map(sample, SampleEntity.class);
        return repository.save(entity) != null;
    }

    @Override
    public boolean update(Sample sample) {
        final SampleEntity entity = modelMapper.map(sample, SampleEntity.class);
        return repository.save(entity) != null;
    }

    @Override
    public boolean delete(Integer id) {
        final SampleEntity entity = repository.findById(id).get();
        if (entity == null)
        {
            return false;
        }
        repository.delete(entity);
        return true;
    }

    @Override
    public Sample findById(Integer id) {
        return modelMapper.map(repository.findById(id).get(), Sample.class);
    }

    @Override
    public List<Sample> list() {
        return repository.findAll().stream().map(entity -> modelMapper.map(entity, Sample.class)).collect(Collectors.toList());
    }

}
