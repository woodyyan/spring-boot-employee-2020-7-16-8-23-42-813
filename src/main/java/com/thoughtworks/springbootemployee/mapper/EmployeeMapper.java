package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeRequest;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {
    public EmployeeResponse toResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setAge(employee.getAge());
        response.setCompanyId(employee.getCompanyId());
        response.setGender(employee.getGender());
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setSalary(employee.getSalary());
        return response;
    }

    public Employee toEntity(EmployeeRequest employeeRequest) {
        Employee employee = new Employee();
        employee.setAge(employeeRequest.getAge());
        employee.setCompanyId(employeeRequest.getCompanyId());
        employee.setGender(employeeRequest.getGender());
        employee.setName(employeeRequest.getName());
        employee.setSalary(employeeRequest.getSalary());
        return employee;
    }
}
