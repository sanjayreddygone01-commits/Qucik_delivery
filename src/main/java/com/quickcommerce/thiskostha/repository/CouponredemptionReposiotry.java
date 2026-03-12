package com.quickcommerce.thiskostha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickcommerce.thiskostha.entity.Coupon;
import com.quickcommerce.thiskostha.entity.CouponRedemption;
import com.quickcommerce.thiskostha.entity.Customer;
@Repository
public interface CouponredemptionReposiotry extends JpaRepository<CouponRedemption, Integer>{

	boolean existsByCoupon(Coupon coupon);

	boolean existsByCouponAndCustomer(Coupon coupon, Customer customer);

}
