package com.quickcommerce.thiskostha.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String state;
    private String street;
    private String landmark;
    private String pincode;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Address() {}
    public Address(Long id, String city, String state, String street, String landmark, String pincode) {
        this.id = id; this.city = city; this.state = state; this.street = street; this.landmark = landmark; this.pincode = pincode;
    }

    // Getters & Setters
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getCity() { return city; } public void setCity(String city) { this.city = city; }
    public String getState() { return state; } public void setState(String state) { this.state = state; }
    public String getStreet() { return street; } public void setStreet(String street) { this.street = street; }
    public String getLandmark() { return landmark; } public void setLandmark(String landmark) { this.landmark = landmark; }
    public String getPincode() { return pincode; } public void setPincode(String pincode) { this.pincode = pincode; }
    public Customer getCustomer() { return customer; } public void setCustomer(Customer customer) { this.customer = customer; }
}