package com.quickcommerce.thiskostha.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Coupon {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String type;
	 private String status;         // ACTIVE / INACTIVE
	    private double offer;          // 10%
	    private double minOrderPrice;  // 500
	    private double maxRedeemPrice; // 300
	    private int maxCoupons;        // 100
	    private LocalDate expiryDate;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public double getOffer() {
			return offer;
		}
		public void setOffer(double offer) {
			this.offer = offer;
		}
		public double getMinOrderPrice() {
			return minOrderPrice;
		}
		public void setMinOrderPrice(double minOrderPrice) {
			this.minOrderPrice = minOrderPrice;
		}
		public double getMaxRedeemPrice() {
			return maxRedeemPrice;
		}
		public void setMaxRedeemPrice(double maxRedeemPrice) {
			this.maxRedeemPrice = maxRedeemPrice;
		}
		public int getMaxCoupons() {
			return maxCoupons;
		}
		public void setMaxCoupons(int maxCoupons) {
			this.maxCoupons = maxCoupons;
		}
		public LocalDate getExpiryDate() {
			return expiryDate;
		}
		public void setExpiryDate(LocalDate expiryDate) {
			this.expiryDate = expiryDate;
		}
		public Coupon(int id, String name, String type, String status, double offer, double minOrderPrice,
				double maxRedeemPrice, int maxCoupons, LocalDate expiryDate) {
			super();
			this.id = id;
			this.name = name;
			this.type = type;
			this.status = status;
			this.offer = offer;
			this.minOrderPrice = minOrderPrice;
			this.maxRedeemPrice = maxRedeemPrice;
			this.maxCoupons = maxCoupons;
			this.expiryDate = expiryDate;
		}
		public Coupon() {
			super();
		}
		@Override
		public String toString() {
			return "Coupon [id=" + id + ", name=" + name + ", type=" + type + ", status=" + status + ", offer=" + offer
					+ ", minOrderPrice=" + minOrderPrice + ", maxRedeemPrice=" + maxRedeemPrice + ", maxCoupons="
					+ maxCoupons + ", expiryDate=" + expiryDate + "]";
		}
	    
	    
	
	

}
