package com.quickcommerce.thiskostha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickcommerce.thiskostha.dto.CustomerDTO;
import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.Customer;
import com.quickcommerce.thiskostha.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<Customer>> register(@RequestBody CustomerDTO customerdto){
		return customerService.register(customerdto);
		
	}
	
	@GetMapping("/findcustomer/{phoneno}")
	public ResponseEntity<ResponseStructure<Customer>> findCustomer(@RequestParam String phone){
		return customerService.findCustomer(phone);
		
	}
	
	@DeleteMapping("//deletecustomer/{phoneno}")
	public ResponseEntity<ResponseStructure<Customer>> deleteCustomer(@RequestParam String phone){
		return customerService.deleteCustomer(phone);
		
	}
	
	

}
