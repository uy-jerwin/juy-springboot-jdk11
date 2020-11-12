package juy.web.controller;

import juy.repository.model.Sample;
import juy.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SampleService sampleService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Sample> list()
    {
        return sampleService.list();
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Sample findById(@PathVariable("id") Integer id)
    {
        return sampleService.findById(id);
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> delete(@PathVariable("id") Integer id)
    {
        if (sampleService.delete(id))
        {
            return ResponseEntity.ok("");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Sample create(@RequestBody Sample sample)
    {
        return sampleService.save(sample);
    }

    @PutMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Sample replace(@RequestBody Sample sample)
    {
        sampleService.replace(sample);
        return sample;
    }

    @PatchMapping(consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Sample update(@RequestBody Sample sample)
    {
        sampleService.update(sample);
        return sample;
    }

}

