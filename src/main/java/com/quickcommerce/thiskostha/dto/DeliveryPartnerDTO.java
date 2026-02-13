package com.quickcommerce.thiskostha.dto;

public class DeliveryPartnerDTO {
	
	 private String name;
	 private String phone;
	 private String email;
	 private String vehicleno;
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
	 public String getVehicleno() {
		 return vehicleno;
	 }
	 public void setVehicleno(String vehicleno) {
		 this.vehicleno = vehicleno;
	 }
	 public DeliveryPartnerDTO(String name, String phone, String email, String vehicleno) {
		super();
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.vehicleno = vehicleno;
	 }
	 public DeliveryPartnerDTO() {
		super();
	 }
	 
	 
}
