package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private final List<Company> companies = new ArrayList<>();

    @PostConstruct
    private void addCompanies() {
        List<Employee> employeesInSpring = new ArrayList<>();
        employeesInSpring.add(new Employee(10, "spring1", 20, "Male"));
        employeesInSpring.add(new Employee(11, "spring2", 19, "Male"));
        employeesInSpring.add(new Employee(12, "spring3", 15, "Male"));

        List<Employee> employeesInBoot = new ArrayList<>();
        employeesInBoot.add(new Employee(13, "boot1", 16, "Male"));
        employeesInBoot.add(new Employee(14, "boot2", 15, "Male"));

        companies.add(new Company(0, "spring", 3, employeesInSpring));
        companies.add(new Company(1, "boot", 2, employeesInBoot));
    }

    public List<Company> findAll() {
        return companies;
    }

    public List<Company> findAll(Integer page, Integer pageSize) {
        int fromIndex = (page - 1) * pageSize;
        int toIndex = Math.min((fromIndex + pageSize), companies.size());
        return companies.subList(fromIndex, toIndex);
    }

    public Company findById(Integer companyId) {
        return companies.stream()
            .filter(company -> company.getId().equals(companyId))
            .findFirst()
            .orElse(null);
    }

    public Company add(Company company) {
        companies.add(company);
        return company;
    }

    public void delete(Company company) {
        companies.remove(company);
    }
}
