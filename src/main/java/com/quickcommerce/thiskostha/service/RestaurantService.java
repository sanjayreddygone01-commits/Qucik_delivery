package com.quickcommerce.thiskostha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.dto.RestaurantDTO;
import com.quickcommerce.thiskostha.entity.Customer;
import com.quickcommerce.thiskostha.entity.Restaurant;
import com.quickcommerce.thiskostha.repository.RestaurantRepository;

@Service


public class RestaurantService {
	@Autowired
	private  RestaurantRepository restaurantRepo;
	
	public  ResponseEntity<ResponseStructure<Restaurant>> register(RestaurantDTO restaurantdto) {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(restaurantdto.getName());
		restaurant.setPhone(restaurantdto.getPhone());
		restaurant.setEmail(restaurantdto.getEmail());
		restaurant.setDescription(restaurantdto.getDescription());
		restaurant.setPackagefees(restaurantdto.getPackagefees());
		restaurant.setType(restaurantdto.getType());
		
		restaurantRepo.save(restaurant);
		ResponseStructure<Restaurant> rs = new ResponseStructure<Restaurant>();
		rs.setStatuscode(HttpStatus.CREATED.value());
		rs.setMessage("Restauarant saved successfully");
		rs.setData(restaurant);
		
		return new ResponseEntity<ResponseStructure<Restaurant>>(rs,HttpStatus.CREATED);
	}

	

	public ResponseEntity<ResponseStructure<Restaurant>> findrestaurant(String phone) {
		
		Restaurant restaurant =restaurantRepo.findByPhone(phone);
		if(restaurant==null) {throw new RuntimeException();}
		ResponseStructure<Restaurant> rs = new ResponseStructure<Restaurant>();
		rs.setStatuscode(HttpStatus.FOUND.value());
		rs.setMessage("customer fteched successfully");
		rs.setData(restaurant);
		
		return new ResponseEntity<ResponseStructure<Restaurant>>(HttpStatus.FOUND);	
		}
	
	
	public ResponseEntity<ResponseStructure<Restaurant>> deleteCustomer(String phone){
		restaurantRepo.deleteByPhone(phone);
		ResponseStructure<Restaurant> rs = new ResponseStructure<Restaurant>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Restaurant Deleted Successfully");
         
		rs.setData(null);
		return new ResponseEntity<ResponseStructure<Restaurant>>(rs,HttpStatus.OK);
		
	}

}
