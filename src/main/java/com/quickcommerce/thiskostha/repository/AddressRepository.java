package com.quickcommerce.thiskostha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickcommerce.thiskostha.entity.Address;
@Repository

public interface AddressRepository  extends JpaRepository<Address, Long>{

}
