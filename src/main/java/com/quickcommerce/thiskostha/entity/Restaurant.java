package com.quickcommerce.thiskostha.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String name;
    @NotBlank private String phone;
    @Email private String email;
    private String description;
    private Double packagefees;
    private String status;
   
	private String type; 
	private Double penality;
	private Double wallet;

    @OneToMany(mappedBy="restaurant",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> menuItems;

    @OneToMany(mappedBy="restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id")
    private Address address;
    private Boolean isBlocked = false;      // Restaurant blocked due to high penalties
    private Double totalPenalties = 0.0; 

    public Restaurant() {
		super();
	}

	public Restaurant(Long id, @NotBlank String name, @NotBlank String phone, @Email String email, String description,
			Double packagefees, String status, String type, Double penality, Double wallet, List<Item> menuItems,
			List<Order> orders, Address address, Boolean isBlocked, Double totalPenalties) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.description = description;
		this.packagefees = packagefees;

		this.status = status;
		
		this.type = type;
		this.penality = penality;
		this.wallet = wallet;
		this.menuItems = menuItems;
		this.orders = orders;
		this.address = address;
		this.isBlocked = isBlocked;
		this.totalPenalties = totalPenalties;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPackagefees() {
		return packagefees;
	}

	public void setPackagefees(Double packagefees) {
		this.packagefees = packagefees;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {

		this.status = status;
		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getPenality() {
		return penality;
	}

	public void setPenality(Double penality) {
		this.penality = penality;
	}

	public Double getWallet() {
		return wallet;
	}

	public void setWallet(Double wallet) {
		this.wallet = wallet;
	}

	public List<Item> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(List<Item> menuItems) {
		this.menuItems = menuItems;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public Double getTotalPenalties() {
		return totalPenalties;
	}

	public void setTotalPenalties(Double totalPenalties) {
		this.totalPenalties = totalPenalties;
	}
    

	}