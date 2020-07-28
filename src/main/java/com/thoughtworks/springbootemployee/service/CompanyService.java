package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public List<Company> getAll(Integer page, Integer pageSize) {
        return companyRepository.findAll(page, pageSize);
    }

    public Company get(Integer companyId) {
        return companyRepository.findById(companyId);
    }

    public Company create(Company company) {
        return companyRepository.add(company);
    }

    public void deleteEmployeesInCompany(Integer companyId) {
        Company company = companyRepository.findById(companyId);
        company.setEmployees(new ArrayList<>());
    }

    public Company update(Integer companyId, Company companyUpdate) {
        Company company = companyRepository.findById(companyId);
        if (companyUpdate.getCompanyName() != null) {
            company.setCompanyName(companyUpdate.getCompanyName());
        }
        if (companyUpdate.getEmployeeNumber() != null) {
            company.setEmployeeNumber(companyUpdate.getEmployeeNumber());
        }
        if (companyUpdate.getEmployees() != null) {
            company.setEmployees(companyUpdate.getEmployees());
        }
        return company;
    }

    public List<Employee> getEmployees(Integer companyId) {
        Company company = companyRepository.findById(companyId);
        return company.getEmployees();
    }
}
