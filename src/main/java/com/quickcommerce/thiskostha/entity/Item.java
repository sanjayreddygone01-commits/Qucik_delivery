
package com.quickcommerce.thiskostha.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank private String name;
    private String description;
    private Double price;
    private String type; // veg/non-veg
    private Boolean availability;
    private Double rating;
    private Integer numberOfReviews;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Item() {}
    public Item(Long id, String name, String description, Double price, String type, Boolean availability, Double rating, Integer numberOfReviews) {
        this.id = id; this.name = name; this.description = description; this.price = price; this.type = type; this.availability = availability; this.rating = rating; this.numberOfReviews = numberOfReviews;
    }

    // Getters & Setters
    public Long getId() { return id; } public void setId(Long id) { this.id = id; }
    public String getName() { return name; } public void setName(String name) { this.name = name; }
    public String getDescription() { return description; } public void setDescription(String description) { this.description = description; }
    public Double getPrice() { return price; } public void setPrice(Double price) { this.price = price; }
    public String getType() { return type; } public void setType(String type) { this.type = type; }
    public Boolean getAvailability() { return availability; } public void setAvailability(Boolean availability) { this.availability = availability; }
    public Double getRating() { return rating; } public void setRating(Double rating) { this.rating = rating; }
    public Integer getNumberOfReviews() { return numberOfReviews; } public void setNumberOfReviews(Integer numberOfReviews) { this.numberOfReviews = numberOfReviews; }
    public Restaurant getRestaurant() { return restaurant; } public void setRestaurant(Restaurant restaurant) { this.restaurant = restaurant; }
}