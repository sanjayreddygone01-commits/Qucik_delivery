package com.quickcommerce.thiskostha.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RestaurantDTO {
	
	  private String name;
	    private String phone;
	     private String email;
	    private String address;
	    private String description;
	    private int packagefees;
	   
		private String type;

		
		
		
		public RestaurantDTO(String name, String phone, String email, String address, String description,
				int packagefees, String type) {
			super();
			this.name = name;
			this.phone = phone;
			this.email = email;
			this.address = address;
			this.description = description;
			this.packagefees = packagefees;
			this.type = type;
		}
		
		

		public RestaurantDTO() {
			super();
			// TODO Auto-generated constructor stub
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

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
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

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		} 
		
		
		
}
