package com.quickcommerce.thiskostha.service;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.dto.RestaurantDTO;
import com.quickcommerce.thiskostha.entity.Address;
import com.quickcommerce.thiskostha.entity.Customer;
import com.quickcommerce.thiskostha.entity.Item;
import com.quickcommerce.thiskostha.entity.Restaurant;
import com.quickcommerce.thiskostha.repository.RestaurantRepository;

@Service
public class RestaurantService {
	@Autowired
	private  RestaurantRepository restaurantRepo;
	@Autowired
	private RestTemplate restTemplate;
	
	public  ResponseEntity<ResponseStructure<Restaurant>> register(RestaurantDTO restaurantdto) {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(restaurantdto.getName());
		restaurant.setPhone(restaurantdto.getPhone());
		restaurant.setEmail(restaurantdto.getEmail());
		restaurant.setDescription(restaurantdto.getDescription());
		restaurant.setPackagefees(restaurantdto.getPackagefees());
		restaurant.setType(restaurantdto.getType());
		
		String url="https://us1.locationiq.com/v1/reverse?key=pk.e13376a26985e3fd5361223a1ed9aabb&lat="+ restaurantdto.getCordinates().getLatitude()+"&lon="
		+restaurantdto.getCordinates().getLongitude()+"&format=json&";
		
		Map<String,Object> response=restTemplate.getForObject(url,Map.class);
		
		Map<String,Object> addressMap=(Map<String,Object>) response.get("address");
		
		Address address=new Address();

		address.setLatitude(restaurantdto.getCordinates().getLatitude());
		address.setLongitude(restaurantdto.getCordinates().getLongitude());

		address.setArea((String) addressMap.get("suburb"));
		address.setCity((String) addressMap.get("city"));
		address.setDistrict((String) addressMap.get("county"));
		address.setState((String) addressMap.get("state"));
		address.setCountry((String) addressMap.get("country"));
		address.setPincode((String) addressMap.get("postcode"));
		

		restaurant.setAddress(address);
		
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
	
	
	public ResponseEntity<ResponseStructure<Restaurant>> deleteRestaurant(String phone){
		restaurantRepo.deleteByPhone(phone);
		ResponseStructure<Restaurant> rs = new ResponseStructure<Restaurant>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Restaurant Deleted Successfully");
         
		rs.setData(null);
		return new ResponseEntity<ResponseStructure<Restaurant>>(rs,HttpStatus.OK);
		
	}
	public ResponseEntity<ResponseStructure<String>> updateStatus(String phone) {
		Restaurant restaurant=restaurantRepo.findByPhone(phone);
		if(restaurant.getStatus() == "Open"){
			restaurant.setStatus("Close");
		}else {
			restaurant.setStatus("Open");
		}
		
		ResponseStructure<String> rs = new ResponseStructure<String>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Restaurant Status updated");
         
		rs.setData(restaurant.getStatus());
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<String>> updateItemAvailability(String phone, Long itemid) {
		Restaurant restaurant =restaurantRepo.findByPhone(phone);
		List<Item> listItem = restaurant.getMenuItems();
		for (Item item : listItem) {
			if(item.getId()==itemid) {
				item.setAvailability("Available");
			}
		}
		restaurantRepo.save(restaurant);
		ResponseStructure<String> rs = new ResponseStructure<String>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Updated Availability Successfully");
         
		rs.setData("Updated Availability"+itemid);
		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseStructure<Item>> addItemToMenu(Item item, String phone) {
		Restaurant restaurant = restaurantRepo.findByPhone(phone);
		Item saveItem = new Item();
		saveItem.setName(item.getName());
		saveItem.setAvailability(item.getAvailability());
		saveItem.setDescription(item.getDescription());
		saveItem.setPrice(item.getPrice());
		saveItem.setType(item.getType());
		saveItem.setRating(item.getRating());
		saveItem.setNumberOfReviews(item.getNumberOfReviews());
		saveItem.setRestaurant(restaurant);
		if(restaurant.getMenuItems()==null) {
			restaurant.setMenuItems(Arrays.asList(saveItem));
		}else {
			restaurant.getMenuItems().add(saveItem);		}
		restaurantRepo.save(restaurant);
		ResponseStructure<Item> rs = new ResponseStructure<Item>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Added Item Successfully");
         
		rs.setData(saveItem);
		return new ResponseEntity<ResponseStructure<Item>>(rs,HttpStatus.OK);
		
	}

}
