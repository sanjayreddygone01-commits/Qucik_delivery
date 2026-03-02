package com.quickcommerce.thiskostha.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "delivery_partners")
public class DeliveryPartner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String email;
    private Double rating;
    private String status;

    @OneToMany(mappedBy = "deliveryPartner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
    private String vehicleNo;
	public DeliveryPartner() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DeliveryPartner(Long id, String name, String phone, String email, Double rating, String status,
			List<Order> orders, String vehicleNo) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.rating = rating;
		this.status = status;
		this.orders = orders;
		this.vehicleNo = vehicleNo;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Order> getOrders() {
		return orders;
	}
	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

   
    
}