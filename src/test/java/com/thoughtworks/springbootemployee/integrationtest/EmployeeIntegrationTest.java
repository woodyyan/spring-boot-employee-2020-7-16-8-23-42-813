package com.thoughtworks.springbootemployee.integrationtest;

import com.thoughtworks.springbootemployee.model.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void should_get_employee_when_get_given_() throws Exception {
        //given
        Employee employee = new Employee(0, "chen", 18, "female", 999999);
        employeeRepository.save(employee);
        //when

        mockMvc.perform(get("/employees"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").isNumber())
            .andExpect(jsonPath("$[0].name").value("chen"))
            .andExpect(jsonPath("$[0].age").value(18))
            .andExpect(jsonPath("$[0].gender").value("female"))
            .andExpect(jsonPath("$[0].salary").value(999999));
    }

    @Test
    void should_create_employee_when_post_employee_given_employee_info() throws Exception {
        String employeeAsJson = "    {\n" +
            "        \"name\": \"alibaba1\",\n" +
            "        \"age\": 20,\n" +
            "        \"gender\": \"male\",\n" +
            "        \"salary\": 6000\n" +
            "    }";

        mockMvc.perform(post("/employees")
            .contentType(MediaType.APPLICATION_JSON)
            .content(employeeAsJson))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value("alibaba1"))
            .andExpect(jsonPath("$.age").value(20))
            .andExpect(jsonPath("$.gender").value("male"))
            .andExpect(jsonPath("$.salary").value(6000));

        List<Employee> employees = employeeRepository.findAll();
        assertEquals(1, employees.size());
        assertEquals("alibaba1", employees.get(0).getName());
    }
}
