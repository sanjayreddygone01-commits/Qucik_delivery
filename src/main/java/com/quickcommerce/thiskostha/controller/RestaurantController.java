package com.quickcommerce.thiskostha.controller;

import java.util.List;

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

import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.dto.RestaurantDTO;
import com.quickcommerce.thiskostha.entity.Item;
import com.quickcommerce.thiskostha.entity.Order;
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

@GetMapping("/findrestaurant/{phone}")
public ResponseEntity<ResponseStructure<Restaurant>> findRestaurant(@PathVariable String phone){
	return restaurantService.findRestaurant(phone);
	
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
@PostMapping("/acceptOrder")
public List<String> acceptOrder(@RequestParam double latitude,@RequestParam double longitude,@RequestParam long orderid){
	return restaurantService.acceptorder(latitude,longitude,orderid);
}

@PatchMapping("/additemtomenu/{phone}")
public ResponseEntity<ResponseStructure<Item>> addItemToMenu(@RequestBody Item item, @PathVariable String phone) {
	return restaurantService.addItemToMenu(item, phone);
}

@PostMapping("/cancelOrder")
public ResponseEntity<ResponseStructure<Order>> cancelOrder(
    @RequestParam String restaurantPhone, 
    @RequestParam Long orderId,
    @RequestParam(required = false) String reason) {
    return restaurantService.cancelOrderByRestaurant(restaurantPhone, orderId, reason);
}

@PatchMapping("/unblockRestaurant")
public ResponseEntity<ResponseStructure<String>> unblockRestaurant(
    @RequestParam String restaurantPhone) {
    return restaurantService.unblockRestaurant(restaurantPhone);
}

}