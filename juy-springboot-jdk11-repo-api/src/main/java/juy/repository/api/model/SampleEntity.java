package juy.repository.api.model;

import lombok.Data;

import java.util.Date;

@Data
public class SampleEntity {

    private Integer id;
    private String name;
    private String value;
    private Date createdAt;
    private Date updatedAt;

}
