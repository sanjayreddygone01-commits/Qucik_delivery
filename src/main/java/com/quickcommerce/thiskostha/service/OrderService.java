package com.quickcommerce.thiskostha.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.Address;
import com.quickcommerce.thiskostha.entity.CartItem;
import com.quickcommerce.thiskostha.entity.Customer;
import com.quickcommerce.thiskostha.entity.Order;
import com.quickcommerce.thiskostha.repository.CustomerRepository;
import com.quickcommerce.thiskostha.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private OrderRepository oderRepo;

	public ResponseEntity<ResponseStructure<Order>> placeOrder(String phone, String method, String addressType) {
		// TODO Auto-generated method stub
		Customer customer = customerRepo.findByPhone(phone);
        if (customer == null)
            throw new RuntimeException("Customer not found");

        List<CartItem> cartItems = customer.getCart();
        

        if (cartItems.isEmpty())
            throw new RuntimeException("Cart is empty");

        // Calculate total cost
        double total = cartItems.stream()
                .mapToDouble(item -> item.getItem().getPrice() * item.getQuantity())
                .sum();

        // Create order
        Order order = new Order();
        order.setStatus("pending");
        order.setCost(total);
        if (method == "COD")
            order.setPaymentStatus("PENDING");
        else
            order.setPaymentStatus("COMPLETED");
        order.setOrderTime(LocalDateTime.now());
        order.setDeliveryStatus("PENDING");
        order.setRestaurant(customer.getCart().getFirst().getRestaurant());
        order.setCustomer(customer);
        order.setItems(cartItems);
        order.setPickupAddress(customer.getCart().getFirst().getRestaurant().getAddress());
       Address deliveryAddress=null;
       for(Address a : customer.getAddresses()) {
    	   if(a.getAddressType().equals(addressType)) {
    		   deliveryAddress=a;
    	   }
       }
       order.setDeliveryAddress(deliveryAddress);
        

        // payment status
       

        

        // response
        ResponseStructure<Order> response = new ResponseStructure<>();
        response.setStatuscode(HttpStatus.ACCEPTED.value());
        response.setMessage("Order placed successfully");
        response.setData(order);

        return ResponseEntity.ok(response);
    
	}

}
