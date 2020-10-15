package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    public List<EmployeeResponse> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    public Page<EmployeeResponse> getAll(Integer page, Integer pageSize) {
        Page<Employee> employees = employeeRepository.findAll(PageRequest.of(page, pageSize));
        List<EmployeeResponse> responses = employees.getContent().stream().map(employeeMapper::toResponse).collect(Collectors.toList());
        return new PageImpl<>(responses, employees.getPageable(), employees.getTotalElements());
    }

    public EmployeeResponse get(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if (employee.isPresent()) {
            return employeeMapper.toResponse(employee.get());
        }
        throw new EmployeeNotFoundException("Employee ID not found.");
    }

    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        Employee savedEmployee = employeeRepository.save(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(savedEmployee);
    }

    public void delete(Integer employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        employee.ifPresent(employeeRepository::delete);
    }

    public EmployeeResponse update(Integer employeeId, EmployeeRequest updatingEmployee) {
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
            Employee updatedEmployee = employeeRepository.save(employee);
            return employeeMapper.toResponse(updatedEmployee);
        }
        throw new EmployeeNotFoundException("Employee Id Not Found.");
    }

    public List<EmployeeResponse> getByGender(String gender) {
        List<Employee> employees = employeeRepository.findAllByGender(gender);
        return employees.stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }
}
