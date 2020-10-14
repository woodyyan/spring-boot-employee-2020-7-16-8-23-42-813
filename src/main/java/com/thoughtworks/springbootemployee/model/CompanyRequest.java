package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class CompanyRequest {
    private Integer id;
    private String companyName;
    private Integer employeeNumber;

    public CompanyRequest() {
    }

    public CompanyRequest(Integer id, String companyName, Integer employeeNumber, List<Employee> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employeeNumber = employeeNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}
