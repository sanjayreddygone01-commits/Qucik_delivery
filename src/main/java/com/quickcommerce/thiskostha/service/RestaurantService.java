package com.quickcommerce.thiskostha.service;

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
	
	
	public ResponseEntity<ResponseStructure<Restaurant>> deleteCustomer(String phone){
		restaurantRepo.deleteByPhone(phone);
		ResponseStructure<Restaurant> rs = new ResponseStructure<Restaurant>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Restaurant Deleted Successfully");
         
		rs.setData(null);
		return new ResponseEntity<ResponseStructure<Restaurant>>(rs,HttpStatus.OK);
		
	}

}
