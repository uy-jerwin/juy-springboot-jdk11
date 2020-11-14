package juy.repository.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Sample {

    private Integer id;
    @EqualsAndHashCode.Exclude
    private String name;
    @EqualsAndHashCode.Exclude
    private String value;

}
