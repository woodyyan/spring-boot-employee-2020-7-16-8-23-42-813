package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class CompanyRequest {
    private String companyName;
    private Integer employeeNumber;

    public CompanyRequest() {
    }

    public CompanyRequest(String companyName, Integer employeeNumber, List<Employee> employees) {
        this.companyName = companyName;
        this.employeeNumber = employeeNumber;
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
