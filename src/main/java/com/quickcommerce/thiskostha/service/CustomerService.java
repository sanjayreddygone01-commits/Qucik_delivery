package com.quickcommerce.thiskostha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quickcommerce.thiskostha.dto.CustomerDTO;
import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.Customer;
import com.quickcommerce.thiskostha.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepo;

	public ResponseEntity<ResponseStructure<Customer>> register(CustomerDTO customerdto) {
		Customer customer =new Customer();
		customer.setName(customerdto.getName());
		customer.setPhone(customerdto.getPhone());
		customer.setEmail(customerdto.getEmail());
		customer.setGender(customerdto.getGender());
		
		customerRepo.save(customer);
		ResponseStructure<Customer> rs = new ResponseStructure<Customer>();
		rs.setStatuscode(HttpStatus.CREATED.value());
		rs.setMessage("customer saved successfully");
		rs.setData(customer);
		
		return new ResponseEntity<ResponseStructure<Customer>>(rs,HttpStatus.CREATED);
		
	}
	
	
	

}
