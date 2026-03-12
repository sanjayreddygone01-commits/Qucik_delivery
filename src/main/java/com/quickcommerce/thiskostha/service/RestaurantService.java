package com.quickcommerce.thiskostha.service;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.dto.RestaurantDTO;
import com.quickcommerce.thiskostha.entity.Address;
import com.quickcommerce.thiskostha.entity.Customer;
import com.quickcommerce.thiskostha.entity.Item;
import com.quickcommerce.thiskostha.entity.Order;
import com.quickcommerce.thiskostha.entity.OrderStatus;
import com.quickcommerce.thiskostha.entity.Payment;
import com.quickcommerce.thiskostha.entity.Restaurant;
import com.quickcommerce.thiskostha.repository.CustomerRepository;
import com.quickcommerce.thiskostha.repository.OrderRepository;
import com.quickcommerce.thiskostha.repository.RestaurantRepository;

@Service
public class RestaurantService {
	@Autowired
	private  RestaurantRepository restaurantRepo;
	
	  @Autowired
	    private RedisService redisService;
	    @Autowired
	    private RedisTemplate<String,String> redisTemplate;
	    @Autowired
	    private OrderRepository orderRepository;
	    @Autowired
	    private CustomerRepository customerRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
    @Value("${myapp.api.key}")
    private String apiKey;

	
	public  ResponseEntity<ResponseStructure<Restaurant>> register(RestaurantDTO restaurantdto) {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(restaurantdto.getName());
		restaurant.setPhone(restaurantdto.getPhone());
		restaurant.setEmail(restaurantdto.getEmail());
		restaurant.setDescription(restaurantdto.getDescription());
		restaurant.setPackagefees(restaurantdto.getPackagefees());
		restaurant.setType(restaurantdto.getType());
		
		String url="https://us1.locationiq.com/v1/reverse?key="+apiKey+"&lat="+ restaurantdto.getCordinates().getLatitude()+"&lon="
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

	

	public ResponseEntity<ResponseStructure<Restaurant>> findRestaurant(String phone) {
		
		Restaurant restaurant =restaurantRepo.findByPhone(phone);
		if(restaurant==null) {throw new RuntimeException();}
		ResponseStructure<Restaurant> rs = new ResponseStructure<Restaurant>();
		rs.setStatuscode(HttpStatus.FOUND.value());
		rs.setMessage("customer fteched successfully");
		rs.setData(restaurant);
		
		return new ResponseEntity<ResponseStructure<Restaurant>>(rs,HttpStatus.FOUND);	
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
	

	    public List<String> acceptorder(double latitude, double longitude, long orderid) {
	           Order order= orderRepository.findById(orderid).orElseThrow(()->new RuntimeException("Order does not exist"));
	         List<String> nearbyPartners= redisService.findNearbyPartners(latitude,longitude,5.0);
	         String orderKey= "order:"+orderid;
	         for(String partnerid:nearbyPartners){
	             Long size = redisTemplate.opsForSet().add(orderKey, partnerid);
//	             
	         }
	          return nearbyPartners;
	    }
	    
	    public ResponseEntity<ResponseStructure<Order>> cancelOrderByRestaurant(
	    	    String restaurantPhone, 
	    	    Long orderId, 
	    	    String reason) {
	    	    
	    	    // Step 1: Fetch restaurant & order
	    	    Restaurant restaurant = restaurantRepo.findByPhone(restaurantPhone);
	    	    if (restaurant == null) {
	    	        ResponseStructure<Order> rs = new ResponseStructure<>();
	    	        rs.setStatuscode(HttpStatus.NOT_FOUND.value());
	    	        rs.setMessage("Restaurant not found");
	    	        return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);
	    	    }
	    	    
	    	    Order order = orderRepository.findById(orderId)
	    	        .orElseThrow(() -> new RuntimeException("Order not found"));
	    	    
	    	    // Step 2: Verify order belongs to this restaurant
	    	    if (!order.getRestaurant().getId().equals(restaurant.getId())) {
	    	        ResponseStructure<Order> rs = new ResponseStructure<>();
	    	        rs.setStatuscode(HttpStatus.BAD_REQUEST.value());
	    	        rs.setMessage("Order does not belong to this restaurant");
	    	        return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
	    	    }
	    	    
	    	    // Step 3: Check if restaurant is already blocked
	    	    if (restaurant.getIsBlocked() != null && restaurant.getIsBlocked()) {
	    	        ResponseStructure<Order> rs = new ResponseStructure<>();
	    	        rs.setStatuscode(HttpStatus.FORBIDDEN.value());
	    	        rs.setMessage("Restaurant is blocked due to high penalties (≥ ₹1000). Clear penalties to proceed.");
	    	        return new ResponseEntity<>(rs, HttpStatus.FORBIDDEN);
	    	    }
	    	    
	    	    // Step 4: Check if order can be cancelled
	    	    if (order.getDeliveryStatus() == OrderStatus.DELIVERED || 
	    	        order.getDeliveryStatus() == OrderStatus.CANCELLED) {
	    	        ResponseStructure<Order> rs = new ResponseStructure<>();
	    	        rs.setStatuscode(HttpStatus.BAD_REQUEST.value());
	    	        rs.setMessage("Order cannot be cancelled at this stage");
	    	        return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
	    	    }
	    	    
	    	    // Step 5: Calculate 10% penalty
	    	    double penaltyAmount = order.getTotalCost() * 0.10;
	    	    
	    	    // Step 6: Check if restaurant has enough wallet balance (optional - can allow negative)
	    	    Double currentWallet = restaurant.getWallet() != null ? restaurant.getWallet() : 0.0;
	    	    Double newWallet = currentWallet - penaltyAmount;
	    	    
	    	    // Step 7: Deduct penalty from restaurant wallet
	    	    restaurant.setWallet(newWallet);
	    	    
	    	    // Step 8: Update total penalties
	    	    Double totalPenalties = restaurant.getTotalPenalties() != null ? restaurant.getTotalPenalties() : 0.0;
	    	    totalPenalties += penaltyAmount;
	    	    restaurant.setTotalPenalties(totalPenalties);
	    	    
	    	    // Step 9: Block restaurant if penalties >= 1000
	    	    if (totalPenalties >= 1000.0) {
	    	        restaurant.setIsBlocked(true);
	    	        // Log blocking event
	    	        System.out.println("🚫 Restaurant " + restaurant.getName() + " has been BLOCKED due to penalties ≥ ₹1000");
	    	    }
	    	    
	    	    // Step 10: Handle customer refund based on payment method
	    	    Customer customer = order.getCustomer();
	    	    Payment payment = order.getPayment();
	    	    
	    	    if (payment.getMethod().equalsIgnoreCase("COD")) {
	    	        // 🔴 COD: Add 10% penalty to customer wallet
	    	        double customerRefund = penaltyAmount;
	    	        customer.setWallet(customer.getWallet() + customerRefund);
	    	        System.out.println("💰 COD Refund: ₹" + String.format("%.2f", customerRefund) + " added to customer wallet");
	    	    } else {
	    	        // ✅ PAID (Card/Online): Refund total amount + 10% penalty
	    	        double totalRefund = order.getTotalCost() + penaltyAmount;
	    	        customer.setWallet(customer.getWallet() + totalRefund);
	    	        System.out.println("💰 Full Refund: ₹" + String.format("%.2f", totalRefund) + " (Order: ₹" + 
	    	            String.format("%.2f", order.getTotalCost()) + " + Penalty: ₹" + String.format("%.2f", penaltyAmount) + ")");
	    	    }
	    	    
	    	    // Step 11: Update order status to CANCELLED
	    	    order.setDeliveryStatus(OrderStatus.CANCELLED);
	    	    
	    	    // Step 12: Save updates
	    	    orderRepository.save(order);
	    	    restaurantRepo.save(restaurant);
	    	    customerRepo.save(customer);
	    	    
	    	    // Step 13: Build response
	    	    ResponseStructure<Order> rs = new ResponseStructure<>();
	    	    rs.setStatuscode(HttpStatus.OK.value());
	    	    
	    	    String message = "Order cancelled successfully. Penalty: ₹" + String.format("%.2f", penaltyAmount);
	    	    if (totalPenalties >= 1000.0) {
	    	        message += ". ⚠️  Restaurant BLOCKED - Total penalties: ₹" + String.format("%.2f", totalPenalties);
	    	    }
	    	    
	    	    rs.setMessage(message);
	    	    rs.setData(order);
	    	    
	    	    return new ResponseEntity<>(rs, HttpStatus.OK);
	    	}

	    	/**
	    	 * Unblock restaurant if penalties are cleared (admin only)
	    	 */
	    	public ResponseEntity<ResponseStructure<String>> unblockRestaurant(String restaurantPhone) {
	    	    Restaurant restaurant = restaurantRepo.findByPhone(restaurantPhone);
	    	    if (restaurant == null) {
	    	        ResponseStructure<String> rs = new ResponseStructure<>();
	    	        rs.setStatuscode(HttpStatus.NOT_FOUND.value());
	    	        rs.setMessage("Restaurant not found");
	    	        return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);
	    	    }
	    	    
	    	    // Check if total penalties are cleared
	    	    Double totalPenalties = restaurant.getTotalPenalties() != null ? restaurant.getTotalPenalties() : 0.0;
	    	    if (totalPenalties > 0.0) {
	    	        ResponseStructure<String> rs = new ResponseStructure<>();
	    	        rs.setStatuscode(HttpStatus.BAD_REQUEST.value());
	    	        rs.setMessage("Restaurant still has pending penalties: ₹" + String.format("%.2f", totalPenalties));
	    	        return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
	    	    }
	    	    
	    	    // Unblock restaurant
	    	    restaurant.setIsBlocked(false);
	    	    restaurantRepo.save(restaurant);
	    	    
	    	    ResponseStructure<String> rs = new ResponseStructure<>();
	    	    rs.setStatuscode(HttpStatus.OK.value());
	    	    rs.setMessage("Restaurant unblocked successfully");
	    	    return new ResponseEntity<>(rs, HttpStatus.OK);
	    	}

	

}
