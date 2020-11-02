package ru.job4j.auth.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.AuthApplication;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = AuthApplication.class)
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private EmployeeRepository repository;

    @Test
    void findAll() throws Exception {
        when(repository.findAll()).thenReturn(List.of(new Employee(0,
                "emp",
                "emp",
                123,
                LocalDate.of(2002, 1, 1))));
        mockMvc.perform(get("/employee/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json("[{\"id\":0," +
                                "\"name\":\"emp\"," +
                                "\"lastName\":\"emp\"," +
                                "\"inn\":123," +
                                "\"hiring_date\":\"2002-01-01\"," +
                                "\"accounts\":[]}]"));
    }

    @Test
    void create() throws Exception {
        when(restTemplate.postForObject(anyString(), any(), any()))
                .thenReturn(new Person(0, "Ivan", "pass"));
        mockMvc.perform(post("/employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":0,\"login\":\"Ivan\",\"password\":\"pass\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"id\":0,\"login\":\"Ivan\",\"password\":\"pass\"}"));
    }

    @Test
    void update() throws Exception {
        doNothing().when(restTemplate).put(anyString(), any());
        mockMvc.perform(put("/employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":0,\"login\":\"Ivan\",\"password\":\"pass\"}"))
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        doNothing().when(restTemplate).delete(anyString(), anyInt());
        mockMvc.perform(request(HttpMethod.DELETE, "/employee/0"))
                .andExpect(status().isOk());
    }
}