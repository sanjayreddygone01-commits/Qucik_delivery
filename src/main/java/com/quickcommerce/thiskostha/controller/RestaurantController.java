package com.quickcommerce.thiskostha.controller;

import org.springframework.http.ResponseEntity;
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
@PostMapping("/register")
public ResponseEntity<ResponseStructure<Restaurant>> register (@RequestBody RestaurantDTO restaurantdto){
	return RestaurantService.register(restaurantdto);
}
}
