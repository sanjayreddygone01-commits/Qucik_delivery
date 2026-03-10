package com.quickcommerce.thiskostha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickcommerce.thiskostha.entity.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Integer>{

}
