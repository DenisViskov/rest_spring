package ru.job4j.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.EmployeeRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Денис Висков
 * @version 1.0
 * @since 02.11.2020
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private RestTemplate restTemplate;
    private EmployeeRepository repository;
    private static final String API = "http://localhost:8080/person/";
    private static final String API_ID = "http://localhost:8080/person/{id}";

    @Autowired
    public EmployeeController(RestTemplate restTemplate, EmployeeRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        List<Employee> result = new ArrayList<>();
        repository.findAll().forEach(result::add);
        return result;
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person result = restTemplate.postForObject(API, person, Person.class);
        return new ResponseEntity<>(
                result,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        restTemplate.put(API, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        restTemplate.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }

}
