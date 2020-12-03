package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.entity.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Page<Employee> getAll(Integer page, Integer pageSize) {
        return employeeRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Employee get(String employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new EmployeeNotFoundException("Employee ID not found."));
    }

    public Employee create(Employee employeeRequest) {
        return employeeRepository.save(employeeRequest);
    }

    public void delete(String employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        employee.ifPresent(employeeRepository::delete);
    }

    public Employee update(String employeeId, Employee updatingEmployee) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            if (updatingEmployee.getName() != null) {
                employee.setName(updatingEmployee.getName());
            }
            if (updatingEmployee.getAge() != null) {
                employee.setAge(updatingEmployee.getAge());
            }
            if (updatingEmployee.getGender() != null) {
                employee.setGender(updatingEmployee.getGender());
            }
            return employeeRepository.save(employee);
        }
        throw new EmployeeNotFoundException("Employee Id Not Found.");
    }

    public List<Employee> getByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }
}
