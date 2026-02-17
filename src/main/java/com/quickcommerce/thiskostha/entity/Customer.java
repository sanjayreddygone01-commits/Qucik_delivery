package com.quickcommerce.thiskostha.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String name;
    @NotBlank private String phone;
    @Email private String email;
    private String gender;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @ManyToMany
    @JoinTable(
        name = "customer_cart_items",
        joinColumns = @JoinColumn(name = "customer_id"),
        inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> cartItems;

    public Customer() {}
    public Customer(Long id, String name, String phone, String email, String gender) {
        this.id = id; this.name = name; this.phone = phone; this.email = email; this.gender = gender;
    }

    // Getters & Setters
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; } public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; } public void setEmail(String email) { this.email = email; }
    public String getGender() { return gender; } public void setGender(String gender) { this.gender = gender; }
    public List<Address> getAddresses() { return addresses; } public void setAddresses(List<Address> addresses) { this.addresses = addresses; }
    public List<Order> getOrders() { return orders; } public void setOrders(List<Order> orders) { this.orders = orders; }
    public List<Item> getCartItems() { return cartItems; } public void setCartItems(List<Item> cartItems) { this.cartItems = cartItems; }
}