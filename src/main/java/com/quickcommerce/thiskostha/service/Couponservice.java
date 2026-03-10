package com.quickcommerce.thiskostha.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.Coupon;
import com.quickcommerce.thiskostha.repository.CouponRepository;
import com.quickcommerce.thiskostha.repository.CouponredemptionReposiotry;

@Service
public class Couponservice {
	@Autowired
	private CouponRepository couponrepoo;
	
	@Autowired
	private CouponredemptionReposiotry couponrederepoo;
	
    public ResponseEntity<ResponseStructure<Coupon>> createCoupon(Coupon coupon){

        Coupon savedCoupon = couponrepoo.save(coupon);

        ResponseStructure<Coupon> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.CREATED.value());
        rs.setMessage("Coupon Created Successfully");
        rs.setData(savedCoupon);

        return new ResponseEntity<>(rs,HttpStatus.CREATED);
    }
    
    

    public ResponseEntity<ResponseStructure<String>> deleteCoupon(Integer couponId){

        Coupon coupon = couponrepoo.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        
        
        if(couponrederepoo.existsByCoupon(coupon)){
            throw new RuntimeException("Coupon already used by customers, cannot delete");
        }

        couponrepoo.delete(coupon);

        ResponseStructure<String> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Coupon Deleted Successfully");
        rs.setData("Deleted");

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

 

    public ResponseEntity<ResponseStructure<Coupon>> updateCoupon(
            Integer couponId,
            String expiryDate){

        Coupon coupon = couponrepoo.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        boolean used = couponrederepoo.existsByCoupon(coupon);

        // If nobody used coupon → extend expiry
        if(!used){
            coupon.setExpiryDate(LocalDate.parse(expiryDate));
        }
        else{
            // If used → reduce maxCoupons
            if(coupon.getMaxCoupons() > 0){
                coupon.setMaxCoupons(coupon.getMaxCoupons() - 1);
            }
        }

        couponrepoo.save(coupon);

        ResponseStructure<Coupon> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Coupon Updated Successfully");
        rs.setData(coupon);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }



   

    public ResponseEntity<ResponseStructure<Coupon>> findCoupon(Integer couponId){

        Coupon coupon = couponrepoo.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found"));

        ResponseStructure<Coupon> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Coupon Fetched Successfully");
        rs.setData(coupon);

        return new ResponseEntity<>(rs,HttpStatus.OK);
    }

	
}
