package com.quickcommerce.thiskostha.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class CartItem {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 @OneToOne
	 private Item item;
	 private int quantity;
	
	 public CartItem(Long id, Item item, int quantity, Customer customer) {
		super();
		this.id = id;
		this.item = item;
		this.quantity = quantity;
		
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
	 
}
