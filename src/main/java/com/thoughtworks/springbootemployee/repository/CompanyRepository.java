package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private final List<Company> companies = new ArrayList<>();

    public List<Company> findAll() {
        return companies;
    }

    public Page<Company> findAll(Pageable pageable) {
        List<Company> companies = this.companies.stream()
            .skip(pageable.getOffset()).limit(pageable.getPageSize()).collect(Collectors.toList());
        return new PageImpl<>(companies, pageable, this.companies.size());
    }

    public Optional<Company> findById(Integer companyId) {
        return companies.stream().filter(company -> company.getId().equals(companyId)).findFirst();
    }

    public Company save(Company company) {
        companies.add(company);
        return company;
    }

    public void delete(Company company) {
        Optional<Company> optionalCompany = companies.stream()
            .filter(item -> item.getId().equals(company.getId())).findFirst();
        optionalCompany.ifPresent(companies::remove);
    }
}
