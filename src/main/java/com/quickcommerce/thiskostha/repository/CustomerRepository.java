package com.quickcommerce.thiskostha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickcommerce.thiskostha.entity.Customer;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
