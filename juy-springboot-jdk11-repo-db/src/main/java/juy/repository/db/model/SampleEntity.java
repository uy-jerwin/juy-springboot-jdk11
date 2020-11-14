package juy.repository.db.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "SAMPLE", schema = "PUBLIC")
@Entity(name = "SAMPLE")
public class SampleEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @EqualsAndHashCode.Exclude
    @Column(name = "NAME")
    private String name;
    @EqualsAndHashCode.Exclude
    @Column(name = "VALUE")
    private String value;

}
