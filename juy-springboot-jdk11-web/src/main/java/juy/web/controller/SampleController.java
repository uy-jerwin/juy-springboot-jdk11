package juy.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/sample")
public class SampleController {

    private final List<Sample> samples = new ArrayList<>();
    private AtomicInteger index = new AtomicInteger(0);

    @PostConstruct
    public void init()
    {
        for (int i = 0; i < 10; i++)
        {
           samples.add(Sample.create(i, "name_" + i, UUID.randomUUID().toString()));
           index.incrementAndGet();
        }
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Sample> list()
    {
        return samples;
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Sample findById(@PathVariable("id") Integer id)
    {
        return samples.stream().filter( e -> id.equals(e.getId())).findFirst().get();
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> delete(@PathVariable("id") Integer id)
    {
        if (samples.remove(id))
        {
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public Sample create(@RequestBody Sample sample)
    {
        sample.setId(index.incrementAndGet());
        samples.add(sample);
        return sample;
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public Sample replace(@RequestBody Sample sample)
    {
        if (samples.remove(sample))
        {
            samples.add(sample);
        }
        return sample;
    }

    @PatchMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public Sample update(@RequestBody Sample sample)
    {
        final int reference = samples.indexOf(sample);
        if (reference != -1)
        {
            final Sample existing = samples.get(reference);
            existing.setName(sample.getName());
            existing.setValue(sample.getValue());
        }
        return sample;
    }

}

class Sample {

    private Integer id;
    private String name;
    private String value;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Sample create(final Integer id, final String name, final String value)
    {
        final Sample sample = new Sample();
        sample.id = id;
        sample.name = name;
        sample.value = value;
        return sample;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sample sample = (Sample) o;
        return id.equals(sample.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

