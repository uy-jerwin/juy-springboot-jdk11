package juy.repository.db.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.annotation.processing.Generated;
import javax.persistence.*;

@Data
@Entity(name = "SAMPLE")
public class Sample {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @EqualsAndHashCode.Exclude
    @Column(name = "NAME")
    private String name;
    @EqualsAndHashCode.Exclude
    @Column(name = "VALUE")
    private String value;

}
