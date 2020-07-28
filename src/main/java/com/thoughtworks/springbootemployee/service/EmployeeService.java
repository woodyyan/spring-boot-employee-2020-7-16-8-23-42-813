package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> getAll(Integer page, Integer pageSize) {
        return employeeRepository.findAll(page, pageSize);
    }

    public Employee get(Integer employeeId) {
        return employeeRepository.findById(employeeId);
    }

    public Employee create(Employee employee) {
        return employeeRepository.add(employee);
    }

    public void delete(Integer employeeId) {
        Employee employee = employeeRepository.findById(employeeId);
        employeeRepository.delete(employee);
    }

    public Employee update(Integer employeeId, Employee employeeUpdate) {
        Employee employee = employeeRepository.findById(employeeId);
        if (employeeUpdate.getName() != null) {
            employee.setName(employeeUpdate.getName());
        }
        if (employeeUpdate.getAge() != null) {
            employee.setAge(employeeUpdate.getAge());
        }
        if (employeeUpdate.getGender() != null) {
            employee.setGender(employeeUpdate.getGender());
        }
        return employee;
    }

    public List<Employee> getByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }
}
