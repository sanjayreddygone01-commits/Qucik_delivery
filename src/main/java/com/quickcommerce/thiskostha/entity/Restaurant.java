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
    private int packagefees;
    private String Status;
   
	private String type; 

    @OneToMany(mappedBy="restaurant",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> menuItems;

    @OneToMany(mappedBy="restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;
    @OneToOne
    @JoinColumn(name="address_id")
    private Address address;

    public Restaurant() {
		super();
	}

	public Restaurant(Long id, @NotBlank String name, @NotBlank String phone, @Email String email, String description,
			int packagefees, String status, String type, List<Item> menuItems, List<Order> orders, Address address) {
		super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.description = description;
		this.packagefees = packagefees;
		Status = status;
		this.type = type;
		this.menuItems = menuItems;
		this.orders = orders;
		this.address = address;
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

	public int getPackagefees() {
		return packagefees;
	}

	public void setPackagefees(int packagefees) {
		this.packagefees = packagefees;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

    
}