package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
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
@RequestMapping("/companies")
public class CompanyController {
    private final List<Company> companies = new ArrayList<>();

    @GetMapping
    public List<Company> getAll() {
        return companies;
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getAll(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer pageSize
    ) {
        return companies.stream().skip(pageSize * page).limit(pageSize).collect(Collectors.toList());
    }

    @GetMapping("/{companyId}")
    public Company get(@PathVariable Integer companyId) {
        return companies.stream().filter(company -> company.getId().equals(companyId)).findFirst().orElse(null);
    }

    @GetMapping("/{companyId}/employees")
    public List<Employee> getEmployees(@PathVariable Integer companyId) {
        Optional<Company> companyOptional = companies.stream().filter(company -> company.getId().equals(companyId)).findFirst();
        if (companyOptional.isPresent()) {
            return companyOptional.get().getEmployees();
        }
        return new ArrayList<>();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Company create(@RequestBody Company companyRequest) {
        companies.add(companyRequest);
        return companyRequest;
    }

    @DeleteMapping("/{companyId}")
    public void deleteCompany(@PathVariable Integer companyId) {
        Optional<Company> deletingCompany = companies.stream()
            .filter(company -> company.getId().equals(companyId)).findFirst();
        deletingCompany.ifPresent(companies::remove);
    }

    @PutMapping("/{companyId}")
    public Company update(@PathVariable Integer companyId, @RequestBody Company companyRequest) {
        Optional<Company> updatingEmployee = companies.stream()
            .filter(company -> company.getId().equals(companyId)).findFirst();
        if (updatingEmployee.isPresent()) {
            companies.remove(updatingEmployee.get());
            companies.add(companyRequest);
        }
        return companyRequest;
    }
}
