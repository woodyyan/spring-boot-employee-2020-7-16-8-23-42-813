package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;

class EmployeeServiceTest {

    @Test
    void should_get_all_employees_when_get_all_given_no_parameters() {
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        Mockito.when(repository.findAll()).thenReturn(asList(new Employee(), new Employee()));
        EmployeeService service = new EmployeeService(repository);
        List<Employee> employeeResponses = service.getAll();

        Assertions.assertEquals(2, employeeResponses.size());
    }

    @Test
    void should_get_all_employees_given_page_number_and_size() {
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);
        int page = 1;
        int size = 10;
        List<Employee> employees = asList(new Employee(), new Employee());
        PageRequest pageable = PageRequest.of(page, size);
        Page<Employee> expected = new PageImpl<>(employees, pageable, 2);
        Mockito.when(repository.findAll(pageable)).thenReturn(expected);

        Page<Employee> employeeResponses = service.getAll(page, size);

        Assertions.assertEquals(10, employeeResponses.getSize());
        Assertions.assertEquals(1, employeeResponses.getNumber());
        Assertions.assertEquals(2, employeeResponses.getContent().size());
    }

    @Test
    void should_get_employee_given_employee_id() {
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(new Employee(1, "Tom", 18, "Male", 1000)));

        Employee response = service.get(1);

        Assertions.assertEquals(1, response.getId());
        Assertions.assertEquals("Tom", response.getName());
    }

    @Test
    void should_create_employee_given_employe_request() {
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);

        Employee savedEmployee = new Employee(1, "Tom", 18, "Male", 1000);
        Mockito.when(repository.save(Mockito.any(Employee.class))).thenReturn(savedEmployee);
        Employee request = new Employee(1, "Tom", 18, "Male", 1000);

        Employee response = service.create(request);

        Assertions.assertEquals(1, response.getId());
        Assertions.assertEquals("Tom", response.getName());
    }

    @Test
    void should_delete_employee_given_employee_id() {
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(new Employee()));

        service.delete(1);

        Mockito.verify(repository, Mockito.times(1)).delete(Mockito.any(Employee.class));
    }

    @Test
    void should_update_employee_given_employee_id_and_employee_request() {
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(new Employee(1, "Jack", 18, "Male", 1000)));
        Mockito.when(repository.save(Mockito.any(Employee.class))).thenReturn(new Employee(1, "Tom", 18, "Male", 1000));

        Employee request = new Employee();
        Employee response = service.update(1, request);

        Assertions.assertEquals(1, response.getId());
        Assertions.assertEquals("Tom", response.getName());
    }

    @Test
    void should_get_employee_by_gender_given_gender() {
        EmployeeRepository repository = Mockito.mock(EmployeeRepository.class);
        EmployeeService service = new EmployeeService(repository);

        Mockito.when(repository.findAllByGender("Male")).thenReturn(asList(new Employee(), new Employee()));

        List<Employee> responses = service.getByGender("Male");

        Assertions.assertEquals(2, responses.size());
    }
}