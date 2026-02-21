package com.quickcommerce.thiskostha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quickcommerce.thiskostha.dto.CustomerDTO;
import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.CartItem;
import com.quickcommerce.thiskostha.entity.Customer;
import com.quickcommerce.thiskostha.entity.Item;
import com.quickcommerce.thiskostha.repository.CartItemRepository;
import com.quickcommerce.thiskostha.repository.CustomerRepository;
import com.quickcommerce.thiskostha.repository.ItemRepository;

@Service
public class CustomerService {
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CartItemRepository cartItemRepository;

	public ResponseEntity<ResponseStructure<Customer>> register(CustomerDTO customerdto) {
		Customer customer = new Customer();
		customer.setName(customerdto.getName());
		customer.setPhone(customerdto.getPhone());
		customer.setEmail(customerdto.getEmail());
		customer.setGender(customerdto.getGender());

		customerRepo.save(customer);
		ResponseStructure<Customer> rs = new ResponseStructure<Customer>();
		rs.setStatuscode(HttpStatus.CREATED.value());
		rs.setMessage("customer saved successfully");
		rs.setData(customer);

		return new ResponseEntity<ResponseStructure<Customer>>(rs, HttpStatus.CREATED);

	}

	public ResponseEntity<ResponseStructure<Customer>> findCustomer(String phone) {
		Customer customer = customerRepo.findByPhone(phone);
		if (customer == null) {
			throw new RuntimeException();
		}
		ResponseStructure<Customer> rs = new ResponseStructure<Customer>();
		rs.setStatuscode(HttpStatus.FOUND.value());
		rs.setMessage("customer fteched successfully");
		rs.setData(customer);

		return new ResponseEntity<ResponseStructure<Customer>>(rs, HttpStatus.FOUND);
	}

	public ResponseEntity<ResponseStructure<Customer>> deleteCustomer(String phone) {
		customerRepo.deleteByPhone(phone);
		ResponseStructure<Customer> rs = new ResponseStructure<Customer>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("customer deleted successfully");
		rs.setData(null);

		return new ResponseEntity<ResponseStructure<Customer>>(rs, HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<CartItem>> addtocart(String phone, Long itemId,int quantity) {

	    Customer customer = customerRepo.findByPhone(phone);
	            
	    Item item = itemRepository.findById(itemId)
	            .orElseThrow(() -> new RuntimeException("Item not found"));

	    CartItem cartItem = new CartItem();
	    cartItem.setItem(item);
	    cartItem.setQuantity(quantity);
	    

	    cartItemRepository.save(cartItem);
	    
	    if(item.getRestaurant().getId()!= customer.getCart().get(0).getItem().getRestaurant().getId()) {
	    	throw new RuntimeException("not from same restaurant");
	    }
	    
	    customer.getCart().add(cartItem);
	    customerRepo.save(customer);

	    ResponseStructure<CartItem> response = new ResponseStructure<>();
	    response.setStatuscode(HttpStatus.ACCEPTED.value());
	    response.setMessage("Item added to cart");
	    response.setData(cartItem);

	    return ResponseEntity.ok(response);
	}

}
