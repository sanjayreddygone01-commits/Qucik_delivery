package com.quickcommerce.thiskostha.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;
    private Double cost;
    private String paymentStatus;
    private LocalDateTime orderTime;
    private String deliveryStatus;
    private Integer otp;
    private String deliveryInstructions;
    private String specialinstructions;
    private Double deliveryCharges;
    private LocalDateTime deliveryTime;
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
    
    @ManyToOne
    @JoinColumn(name="pickup_address_id")
    private Address pickupAddress;
    
    @ManyToOne
    @JoinColumn(name="delivery_address_id")
    private Address deliveryAddress;

    @ManyToMany
    @JoinTable(
        name = "order_items",
        joinColumns = @JoinColumn(name="order_id"),
        inverseJoinColumns = @JoinColumn(name="item_id")
    )
    private List<CartItem> items;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "delivery_partner_id")
    private DeliveryPartner deliveryPartner;

    @OneToOne(mappedBy="order",cascade = CascadeType.ALL)
    private Payment payment;

	
	public Order(Long id, String status, Double cost, String paymentStatus, LocalDateTime orderTime,
			String deliveryStatus, Integer otp, String deliveryInstructions, String specialinstructions,
			Double deliveryCharges, LocalDateTime deliveryTime, Restaurant restaurant, Address pickupAddress,
			Address deliveryAddress, List<CartItem> items, Customer customer, DeliveryPartner deliveryPartner,
			Payment payment) {
		super();
		this.id = id;
		this.status = status;
		this.cost = cost;
		this.paymentStatus = paymentStatus;
		this.orderTime = orderTime;
		this.deliveryStatus = deliveryStatus;
		this.otp = otp;
		this.deliveryInstructions = deliveryInstructions;
		this.specialinstructions = specialinstructions;
		this.deliveryCharges = deliveryCharges;
		this.deliveryTime = deliveryTime;
		this.restaurant = restaurant;
		this.pickupAddress = pickupAddress;
		this.deliveryAddress = deliveryAddress;
		this.items = items;
		this.customer = customer;
		this.deliveryPartner = deliveryPartner;
		this.payment = payment;
	}

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(LocalDateTime orderTime) {
		this.orderTime = orderTime;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Integer getOtp() {
		return otp;
	}

	public void setOtp(Integer otp) {
		this.otp = otp;
	}

	public String getDeliveryInstructions() {
		return deliveryInstructions;
	}

	public void setDeliveryInstructions(String deliveryInstructions) {
		this.deliveryInstructions = deliveryInstructions;
	}

	public String getSpecialinstructions() {
		return specialinstructions;
	}

	public void setSpecialinstructions(String specialinstructions) {
		this.specialinstructions = specialinstructions;
	}

	public Double getDeliveryCharges() {
		return deliveryCharges;
	}

	public void setDeliveryCharges(Double deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Address getPickupAddress() {
		return pickupAddress;
	}

	public void setPickupAddress(Address pickupAddress) {
		this.pickupAddress = pickupAddress;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public DeliveryPartner getDeliveryPartner() {
		return deliveryPartner;
	}

	public void setDeliveryPartner(DeliveryPartner deliveryPartner) {
		this.deliveryPartner = deliveryPartner;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public LocalDateTime getDeliverTime() {
		return deliveryTime;
	}

	public void setDeliverTime(LocalDateTime deliverTime) {
		this.deliveryTime = deliverTime;
	}

	
	

	
	

}