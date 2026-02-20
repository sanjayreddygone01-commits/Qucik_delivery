package com.quickcommerce.thiskostha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.dto.RestaurantDTO;
import com.quickcommerce.thiskostha.entity.Item;
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

@GetMapping("/findrestaurant/{phoneno}")
public ResponseEntity<ResponseStructure<Restaurant>> findrestaurant(@RequestParam String phone){
	return restaurantService.findrestaurant(phone);
	
}

@DeleteMapping("/deleterestaurant/{phoneno}")
public ResponseEntity<ResponseStructure<Restaurant>> deleteRestaurant(@RequestParam String phone){
	return restaurantService.deleteRestaurant(phone);
	
}
@PatchMapping("/updatestatus{phoneno}")
public ResponseEntity<ResponseStructure<String>> updateStatus(@RequestParam String phone){
	return restaurantService.updateStatus(phone);
	}
@PatchMapping("/updateItemAvailability/{phoneno}/{itemid}")
public ResponseEntity<ResponseStructure<String>> updateItemAvailability(@RequestParam String phone, @RequestParam Long itemid){
	return restaurantService.updateItemAvailability(phone,itemid);
}
@PatchMapping("/additemtomenu/{phone}")
public ResponseEntity<ResponseStructure<Item>> addItemToMenu(@RequestBody Item item, @RequestParam String phone) {
	return restaurantService.addItemToMenu(item, phone);
}
}