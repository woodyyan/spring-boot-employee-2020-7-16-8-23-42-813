package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private CompanyMapper companyMapper;

    public CompanyService(CompanyRepository companyRepository, EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<CompanyResponse> getAll() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(company -> companyMapper.toResponse(company)).collect(Collectors.toList());
    }

    public Page<Company> getAll(Integer page, Integer pageSize) {
        return companyRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Company get(Integer companyId) {
        return companyRepository.findById(companyId).orElse(null);
    }

    public Company create(Company company) {
        return companyRepository.save(company);
    }

    public void deleteCompany(Integer companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        company.ifPresent(companyRepository::delete);
    }

    public Company update(Integer companyId, Company companyUpdate) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            if (companyUpdate.getCompanyName() != null) {
                company.setCompanyName(companyUpdate.getCompanyName());
            }
            if (companyUpdate.getEmployeeNumber() != null) {
                company.setEmployeeNumber(companyUpdate.getEmployeeNumber());
            }
        }
        return company;
    }

    public List<Employee> getEmployees(Integer companyId) {
        return employeeRepository.findAllByCompanyId(companyId);
    }
}
