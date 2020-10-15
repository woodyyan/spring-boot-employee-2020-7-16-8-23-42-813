package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static java.util.Arrays.asList;

class EmployeeServiceTest {

    @Test
    void should_get_all_employees_when_get_all_given_no_parameters() {
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(repository.findAll()).thenReturn(asList(new Employee(), new Employee()));
        EmployeeService service = new EmployeeService(repository, new EmployeeMapper());
        List<EmployeeResponse> employeeResponses = service.getAll();

        Assertions.assertEquals(2, employeeResponses.size());
    }

    @Test
    void should_get_all_employees_given_page_number_and_size() {
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeMapper mapper = Mockito.mock(EmployeeMapper.class);
        EmployeeService service = new EmployeeService(repository, mapper);
        int page = 1;
        int size = 10;
        List<Employee> employees = asList(new Employee(), new Employee());
        PageRequest pageable = PageRequest.of(page, size);
        Page<Employee> expected = new PageImpl<>(employees, pageable, 2);
        Mockito.when(repository.findAll(pageable)).thenReturn(expected);

        Page<EmployeeResponse> employeeResponses = service.getAll(page, size);

        Assertions.assertEquals(10, employeeResponses.getSize());
        Assertions.assertEquals(1, employeeResponses.getNumber());
        Assertions.assertEquals(2, employeeResponses.getContent().size());
    }
}