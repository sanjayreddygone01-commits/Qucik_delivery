package com.quickcommerce.thiskostha.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickcommerce.thiskostha.entity.Payment;
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
