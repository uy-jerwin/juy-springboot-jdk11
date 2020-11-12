package juy.repository;

import juy.repository.model.Sample;

import java.util.List;

public interface SampleRepository {

    Sample save(Sample sample);

    boolean replace(Sample sample);

    boolean update(Sample sample);

    boolean delete(Integer id);
    
    Sample findById(Integer id);

    List<Sample> list();

}
