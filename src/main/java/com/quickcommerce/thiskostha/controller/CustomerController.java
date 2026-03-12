package com.quickcommerce.thiskostha.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.quickcommerce.thiskostha.dto.CartResponse;
import com.quickcommerce.thiskostha.dto.CustomerAddressDTO;
import com.quickcommerce.thiskostha.dto.CustomerDTO;
import com.quickcommerce.thiskostha.dto.OrderConsent;
import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.dto.SearchResponse;
import com.quickcommerce.thiskostha.entity.Address;
import com.quickcommerce.thiskostha.entity.CartItem;
import com.quickcommerce.thiskostha.entity.Customer;
import com.quickcommerce.thiskostha.entity.Order;
import com.quickcommerce.thiskostha.service.CustomerService;
import com.quickcommerce.thiskostha.service.OrderService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private OrderService orderService;
     
	@PostMapping("/register")
	public ResponseEntity<ResponseStructure<Customer>> register(@RequestBody CustomerDTO customerdto) {
		return customerService.register(customerdto);
	}

	@GetMapping("/findcustomer/{phoneno}")
	public ResponseEntity<ResponseStructure<Customer>> findCustomer(@PathVariable String phone) {
		return customerService.findCustomer(phone);
	}

	@DeleteMapping("/deletecustomer/{phone}")
	public ResponseEntity<ResponseStructure<Customer>> deleteCustomer(@PathVariable String phone) {
		return customerService.deleteCustomer(phone);
	}
	
	@PatchMapping("/addAddress/{phone}")
	public ResponseEntity<ResponseStructure<Address>> addAddrress(@PathVariable String phone,@RequestBody CustomerAddressDTO address ) {
		return customerService.addAddress(phone,address);
	}

	@PatchMapping("/addtocart/{phone}/{itemId}/{quantity}")
	public ResponseEntity<ResponseStructure<CartItem>> addtoCart(@PathVariable String phone, @PathVariable Long itemId,
			@PathVariable int quantity) {
		return customerService.addtocart(phone, itemId, quantity);
	}
	
	@GetMapping("/SearchItemOrRestaurant")
	public ResponseEntity<ResponseStructure<SearchResponse>> SearchItemOrRestaurant(@RequestParam String phone, @RequestParam String addressType, @RequestParam String SearchKey){
		return customerService.SearchItemOrRestaurant(phone,addressType,SearchKey);
	}
	
	@GetMapping("/getcart/{phone}")
	public ResponseEntity<ResponseStructure<CartResponse>> getCart(@PathVariable String phone) {
		return customerService.getCart(phone);
	}
	
	@PostMapping("/placeorder")
    public ResponseEntity<ResponseStructure<OrderConsent>> placeOrder(
            @RequestParam String phone,
            @RequestParam String method,
            @RequestParam String addressType, 
            @RequestParam String deliveryInstructions,
            @RequestParam String specialInstructions) {
        return orderService.placeOrder(phone, method, addressType,deliveryInstructions,specialInstructions);
    }
	
	
	 @PostMapping("/ConfirmPlacingOrder")
	    public ResponseEntity<ResponseStructure<Order>> confirmPlacingOrder(@RequestParam Long orderid) {
	       return  orderService.confirmPlacingOrder(orderid);
	        
	    }

	    @PostMapping("/denyPlacingOrder")
	    public ResponseEntity<ResponseStructure<Order>> denyPlacingOrder(@RequestParam Long orderid) {
	        return orderService.denyPlacingOrder(orderid);
        
	    }
	    
        @PostMapping("/cancelOrder")
	    public ResponseEntity<ResponseStructure<Order>> cancelOrder(@RequestParam String phone, @RequestParam Long orderId, @RequestParam String reason) {
	    	return  customerService.cancelOrder(phone, orderId, reason);
	        
	    }
	    
	    @PostMapping("/placeorderWithCoupon")
	    public ResponseEntity<ResponseStructure<Order>> placeOrderWithCoupon(
	        @RequestParam Long orderId,
	        @RequestParam Integer couponId) {
	    	
	    	return orderService.placeOrderWithCoupon(orderId,couponId);
	    }
	    
}