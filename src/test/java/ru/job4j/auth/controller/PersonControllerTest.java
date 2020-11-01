package ru.job4j.auth.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.job4j.auth.AuthApplication;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.PersonRepository;

import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository repository;

    @Test
    void findAll() throws Exception {
        List<Person> persons = List.of(new Person(0, "Ivan", "pass"));
        when(repository.findAll()).thenReturn(persons);
        mockMvc.perform(get("/person/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"id\":0,\"login\":\"Ivan\",\"password\":\"pass\"}]"));
    }

    @Test
    void findById() throws Exception {
        Optional<Person> box = Optional.of(new Person(0, "Ivan", "pass"));
        when(repository.findById(0)).thenReturn(box);
        mockMvc.perform(get("/person/0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":0,\"login\":\"Ivan\",\"password\":\"pass\"}"));
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(post("/person/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("login", "Ivan")
                .param("password", "pass"))
                .andExpect(status().isCreated());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}