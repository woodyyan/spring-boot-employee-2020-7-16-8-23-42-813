package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.entity.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {
}
