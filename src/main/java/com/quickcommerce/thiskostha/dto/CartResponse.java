package com.quickcommerce.thiskostha.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.quickcommerce.thiskostha.entity.CartItem;

public class CartResponse {
	
	
private List <CartItem> items;
private Double distance;
private Double deliveryCharges;
private Double packagingFee;
private LocalDateTime deliveryTime;
private Double totalCost;

public CartResponse(List<CartItem> items, Double distance, Double deliveryCharges, Double packagingFee,
		LocalDateTime deliveryTime, Double totalCost) {
	super();
	this.items = items;
	this.distance = distance;
	this.deliveryCharges = deliveryCharges;
	this.packagingFee = packagingFee;
	this.deliveryTime = deliveryTime;
	this.totalCost = totalCost;
}
public CartResponse() {
	super();
	// TODO Auto-generated constructor stub
}
public List<CartItem> getItems() {
	return items;
}
public void setItems(List<CartItem> items) {
	this.items = items;
}
public Double getDistance() {
	return distance;
}
public void setDistance(Double distance) {
	this.distance = distance;
}
public Double getDeliveryCharges() {
	return deliveryCharges;
}
public void setDeliveryCharges(double i) {
	this.deliveryCharges = i;
}
public Double getPackagingFee() {
	return packagingFee;
}
public void setPackagingFee(Double packagingFee) {
	this.packagingFee = packagingFee;
}
public LocalDateTime getDeliveryTime() {
	return deliveryTime;
}
public void setDeliveryTime(LocalDateTime deliveryTime) {
	this.deliveryTime = deliveryTime;
}
public Double getTotalCost() {
	return totalCost;
}
public void setTotalCost(Double totalCost) {
	this.totalCost = totalCost;
}

}
