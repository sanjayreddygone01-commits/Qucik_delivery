package com.quickcommerce.thiskostha.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class CouponRedemption {
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Coupon coupon;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Order order;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public CouponRedemption(Integer id, Coupon coupon, Customer customer, Order order) {
		super();
		this.id = id;
		this.coupon = coupon;
		this.customer = customer;
		this.order = order;
	}

	public CouponRedemption() {
		super();
	}

	@Override
	public String toString() {
		return "CouponRedmaption [id=" + id + ", coupon=" + coupon + ", customer=" + customer + ", order=" + order
				+ "]";
	}


}
