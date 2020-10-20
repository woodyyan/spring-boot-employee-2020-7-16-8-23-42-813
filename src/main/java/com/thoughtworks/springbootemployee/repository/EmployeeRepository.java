package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    public List<Employee> findAllByGender(String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public List<Employee> findAllByCompanyId(Integer companyId) {
        return employees.stream()
            .filter(employee -> employee.getCompanyId().equals(companyId))
            .collect(Collectors.toList());
    }

    public List<Employee> findAll() {
        return employees;
    }

    public Page<Employee> findAll(Pageable pageable) {
        List<Employee> employees = this.employees.stream()
            .skip(pageable.getOffset()).limit(pageable.getPageSize()).collect(Collectors.toList());
        return new PageImpl<>(employees, pageable, this.employees.size());
    }

    public Optional<Employee> findById(Integer employeeId) {
        return employees.stream().filter(employee -> employee.getId().equals(employeeId)).findFirst();
    }

    public Employee save(Employee employeeRequest) {
        employees.add(employeeRequest);
        return employeeRequest;
    }

    public void delete(Employee employee) {
        Optional<Employee> optionalEmployee = employees.stream()
            .filter(item -> item.getId().equals(employee.getId())).findFirst();
        optionalEmployee.ifPresent(employees::remove);
    }
}
