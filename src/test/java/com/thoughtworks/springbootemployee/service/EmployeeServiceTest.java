package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static java.util.Arrays.asList;

class EmployeeServiceTest {

    @Test
    void should_get_all_employees_when_get_all_given_no_parameters() {
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(repository.findAll()).thenReturn(asList(new Employee(), new Employee()));
        EmployeeService service = new EmployeeService(repository);
        List<Employee> employees = service.getAll();

        Assertions.assertEquals(2, employees.size());
    }
}