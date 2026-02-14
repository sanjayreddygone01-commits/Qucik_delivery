package com.quickcommerce.thiskostha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.dto.RestaurantDTO;
import com.quickcommerce.thiskostha.entity.Restaurant;
import com.quickcommerce.thiskostha.service.RestaurantService;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
	@Autowired
	private RestaurantService restaurantService;
@PostMapping("/register")
public ResponseEntity<ResponseStructure<Restaurant>> register (@RequestBody RestaurantDTO restaurantdto){
	return restaurantService.register(restaurantdto);
}

}
