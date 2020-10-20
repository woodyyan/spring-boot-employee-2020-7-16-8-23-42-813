package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final List<Employee> employees = new ArrayList<>();


    @GetMapping
    public List<Employee> getAll() {
        return employees;
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getPaginatedAll(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer pageSize
    ) {
        return employees.stream().skip(pageSize * page).limit(pageSize).collect(Collectors.toList());
    }

    @GetMapping(params = "gender")
    public List<Employee> getByGender(@RequestParam String gender) {
        return employees.stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    @GetMapping("/{employeeId}")
    public Employee get(@PathVariable Integer employeeId) {
        return employees.stream().filter(employee -> employee.getId().equals(employeeId)).findFirst().orElse(null);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Employee create(@RequestBody Employee employeeRequest) {
        employees.add(employeeRequest);
        return employeeRequest;
    }

    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable Integer employeeId) {
        Optional<Employee> deletingEmployee = employees.stream()
            .filter(employee -> employee.getId().equals(employeeId)).findFirst();
        deletingEmployee.ifPresent(employees::remove);
    }

    @PutMapping("/{employeeId}")
    public Employee update(@PathVariable Integer employeeId, @RequestBody Employee employeeRequest) {
        Optional<Employee> updatingEmployee = employees.stream()
            .filter(employee -> employee.getId().equals(employeeId)).findFirst();
        if (updatingEmployee.isPresent()) {
            employees.remove(updatingEmployee.get());
            employees.add(employeeRequest);
        }
        return employeeRequest;
    }
}
