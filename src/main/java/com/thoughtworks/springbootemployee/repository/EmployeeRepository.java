package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findAllByGender(String gender);

    List<Employee> findAllByCompanyId(Integer companyId);
}
