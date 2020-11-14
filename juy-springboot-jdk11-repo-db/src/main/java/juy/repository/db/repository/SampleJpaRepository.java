package juy.repository.db.repository;

import juy.repository.db.model.SampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleJpaRepository extends JpaRepository<SampleEntity, Integer>
{

}
