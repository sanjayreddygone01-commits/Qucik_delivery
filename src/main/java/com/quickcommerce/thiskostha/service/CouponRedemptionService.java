package com.quickcommerce.thiskostha.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.Coupon;
import com.quickcommerce.thiskostha.entity.CouponRedemption;
import com.quickcommerce.thiskostha.entity.Customer;
import com.quickcommerce.thiskostha.entity.Order;
import com.quickcommerce.thiskostha.repository.CouponRepository;
import com.quickcommerce.thiskostha.repository.CouponredemptionReposiotry;
import com.quickcommerce.thiskostha.repository.OrderRepository;
@Service
public class CouponRedemptionService {
    
    @Autowired
    private CouponredemptionReposiotry couponRedemptionRepo;
    
    @Autowired
    private CouponRepository couponRepo;
    
    @Autowired
    private OrderRepository orderRepository;
    
    /**
     * Validate if a customer can use a coupon
     * @return true if customer hasn't used this coupon before
     */
    public boolean validateCouponUsage(Integer couponId, Long customerId) {
        Coupon coupon = couponRepo.findById(couponId)
            .orElseThrow(() -> new RuntimeException("Coupon not found"));
        
        Customer customer = new Customer();
        customer.setId(customerId);
        
        // Check if customer already used this coupon
        return !couponRedemptionRepo.existsByCouponAndCustomer(coupon, customer);
    }
    
    /**
     * Apply coupon to order and calculate discount
     * @param couponId - ID of the coupon
     * @param orderId - ID of the order
     * @return discount amount
     */
    public ResponseEntity<ResponseStructure<Double>> redeemCoupon(
        Integer couponId, Long orderId) {
        
        Coupon coupon = couponRepo.findById(couponId)
            .orElseThrow(() -> new RuntimeException("Coupon not found"));
        
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new RuntimeException("Order not found"));
        
        Customer customer = order.getCustomer();
        
        // Check if coupon is active
        if (!coupon.getStatus().equalsIgnoreCase("ACTIVE")) {
            ResponseStructure<Double> rs = new ResponseStructure<>();
            rs.setStatuscode(HttpStatus.BAD_REQUEST.value());
            rs.setMessage("Coupon is not active");
            return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
        }
        
        // Check if coupon already used by this customer
        if (couponRedemptionRepo.existsByCouponAndCustomer(coupon, customer)) {
            ResponseStructure<Double> rs = new ResponseStructure<>();
            rs.setStatuscode(HttpStatus.BAD_REQUEST.value());
            rs.setMessage("You have already used this coupon. Cannot reuse!");
            return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
        }
        
        // Check order amount against minimum order price
        if (order.getTotalCost() < coupon.getMinOrderPrice()) {
            ResponseStructure<Double> rs = new ResponseStructure<>();
            rs.setStatuscode(HttpStatus.BAD_REQUEST.value());
            rs.setMessage("Order amount is less than minimum required: " + coupon.getMinOrderPrice());
            return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
        }
        
        // ✅ CALCULATE DISCOUNT
        double discountPercentage = coupon.getOffer(); // e.g., 10 for 10%
        double discountAmount = (order.getTotalCost() * discountPercentage) / 100;
        
        // Cap discount at maxRedeemPrice if specified
        if (discountAmount > coupon.getMaxRedeemPrice()) {
            discountAmount = coupon.getMaxRedeemPrice();
        }
        
        // ✅ APPLY DISCOUNT TO ORDER
        double finalTotal = order.getTotalCost() - discountAmount;
        order.setTotalCost(finalTotal);
        
        // Create redemption record
        CouponRedemption redemption = new CouponRedemption();
        redemption.setCoupon(coupon);
        redemption.setCustomer(customer);
        redemption.setOrder(order);
        
        couponRedemptionRepo.save(redemption);
        orderRepository.save(order);
        
        ResponseStructure<Double> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.OK.value());
        rs.setMessage("Coupon redeemed successfully! Discount applied: ₹" + String.format("%.2f", discountAmount));
        rs.setData(discountAmount); // Return discount amount
        
        return new ResponseEntity<>(rs, HttpStatus.OK);
    }
    
    /**
     * Get discount amount without applying it (preview)
     */
    public double calculateDiscount(Integer couponId, Double orderTotal) {
        Coupon coupon = couponRepo.findById(couponId)
            .orElseThrow(() -> new RuntimeException("Coupon not found"));
        
        double discountAmount = (orderTotal * coupon.getOffer()) / 100;
        
        // Cap at max redeem price
        if (discountAmount > coupon.getMaxRedeemPrice()) {
            discountAmount = coupon.getMaxRedeemPrice();
        }
        
        return discountAmount;
    }
}