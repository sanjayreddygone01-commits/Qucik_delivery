package com.quickcommerce.thiskostha.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class CartItem {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 @OneToMany
	 private Item item;
	 private int quantity;
	 @ManyToMany
	 @JoinColumn(name = "customer_id")
	 private Customer customer;
	 public CartItem(Long id, Item item, int quantity, Customer customer) {
		super();
		this.id = id;
		this.item = item;
		this.quantity = quantity;
		this.customer = customer;
	 }
	 public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	 }
	 public Long getId() {
		 return id;
	 }
	 public void setId(Long id) {
		 this.id = id;
	 }
	 public Item getItem() {
		 return item;
	 }
	 public void setItem(Item item) {
		 this.item = item;
	 }
	 public int getQuantity() {
		 return quantity;
	 }
	 public void setQuantity(int quantity) {
		 this.quantity = quantity;
	 }
	 public Customer getCustomer() {
		 return customer;
	 }
	 public void setCustomer(Customer customer) {
		 this.customer = customer;
	 }

}
