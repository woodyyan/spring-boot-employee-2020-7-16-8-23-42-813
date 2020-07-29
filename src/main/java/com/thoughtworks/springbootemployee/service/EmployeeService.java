package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    public Employee get(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return employee.get();
        }
        throw new EmployeeNotFoundException("Employee ID not found.");
    }

    public Employee create(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void delete(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        employee.ifPresent(employeeRepository::delete);
    }

    public Employee update(Integer employeeId, Employee employeeUpdate) {
        Employee employee = employeeRepository.findById(employeeId).orElse(null);
        if (employee != null) {
            if (employeeUpdate.getName() != null) {
                employee.setName(employeeUpdate.getName());
            }
            if (employeeUpdate.getAge() != null) {
                employee.setAge(employeeUpdate.getAge());
            }
            if (employeeUpdate.getGender() != null) {
                employee.setGender(employeeUpdate.getGender());
            }
        }
        return employee;
    }

    public List<Employee> getByGender(String gender) {
        return employeeRepository.findAllByGender(gender);
    }
}
