package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyRequest;
import com.thoughtworks.springbootemployee.model.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.model.EmployeeResponse;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final CompanyMapper companyMapper;
    private final EmployeeMapper employeeMapper;

    public CompanyService(CompanyRepository companyRepository,
                          EmployeeRepository employeeRepository,
                          CompanyMapper companyMapper,
                          EmployeeMapper employeeMapper) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
        this.companyMapper = companyMapper;
        this.employeeMapper = employeeMapper;
    }

    public List<CompanyResponse> getAll() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }

    public Page<CompanyResponse> getAll(Integer page, Integer pageSize) {
        Page<Company> companies = companyRepository.findAll(PageRequest.of(page, pageSize));
        List<CompanyResponse> responses = companies.getContent().stream()
            .map(companyMapper::toResponse).collect(Collectors.toList());
        return new PageImpl<>(responses, companies.getPageable(), companies.getTotalElements());
    }

    public CompanyResponse get(Integer companyId) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            return companyMapper.toResponse(company);
        }
        throw new CompanyNotFoundException("Company Id Not Found.");
    }

    public CompanyResponse create(CompanyRequest company) {
        Company savedCompany = companyRepository.save(companyMapper.toEntity(company));
        return companyMapper.toResponse(savedCompany);
    }

    public void deleteCompany(Integer companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        company.ifPresent(companyRepository::delete);
    }

    public CompanyResponse update(Integer companyId, CompanyRequest updatingCompany) {
        Company company = companyRepository.findById(companyId).orElse(null);
        if (company != null) {
            if (updatingCompany.getCompanyName() != null) {
                company.setCompanyName(updatingCompany.getCompanyName());
            }
            return companyMapper.toResponse(company);
        }
        throw new CompanyNotFoundException("Company Id Not Found.");
    }

    public List<EmployeeResponse> getEmployees(Integer companyId) {
        List<Employee> employees = employeeRepository.findAllByCompanyId(companyId);
        return employees.stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }
}
