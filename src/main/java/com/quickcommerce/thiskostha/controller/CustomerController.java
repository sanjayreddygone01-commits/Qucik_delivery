package com.quickcommerce.thiskostha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickcommerce.thiskostha.dto.CustomerDTO;
import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.CartItem;
import com.quickcommerce.thiskostha.entity.Customer;
import com.quickcommerce.thiskostha.entity.Restaurant;
import com.quickcommerce.thiskostha.service.CustomerService;
import com.quickcommerce.thiskostha.service.RestaurantService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
     
	@Autowired
	private RestaurantService restaurantService;
	
	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<Customer>> register(@RequestBody CustomerDTO customerdto) {
		return customerService.register(customerdto);

	}

	@GetMapping("/findcustomer/{phoneno}")
	public ResponseEntity<ResponseStructure<Customer>> findCustomer(@PathVariable String phone) {
		return customerService.findCustomer(phone);

	}

	@DeleteMapping("/deletecustomer/{phoneno}")
	public ResponseEntity<ResponseStructure<Customer>> deleteCustomer(@PathVariable String phone) {
		return customerService.deleteCustomer(phone);

	}

	@PatchMapping("/addtocart/{phone}/{itemId}/{quantity}")
	public ResponseEntity<ResponseStructure<CartItem>> addtoCart(@PathVariable String phone, @PathVariable Long itemId,
			@PathVariable int quantity) {
		return customerService.addtocart(phone, itemId, quantity);

	}
	
	@PostMapping("/customer/SearchItemOrRestaurant")
	public ResponseEntity<List<Restaurant>> SearchItemOrRestaurant(@RequestParam String phone, @RequestParam String SearchKey ){
		
		List<Restaurant> result= restaurantService.SearchItemOrRestaurant(phone,SearchKey);
		return new ResponseEntity<>(result, HttpStatus.FOUND);
		
	}

}
