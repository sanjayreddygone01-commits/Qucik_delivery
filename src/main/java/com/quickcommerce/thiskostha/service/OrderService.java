package com.quickcommerce.thiskostha.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.Address;
import com.quickcommerce.thiskostha.entity.CartItem;
import com.quickcommerce.thiskostha.entity.Customer;
import com.quickcommerce.thiskostha.entity.Order;
import com.quickcommerce.thiskostha.entity.OrderStatus;
import com.quickcommerce.thiskostha.entity.Restaurant;
import com.quickcommerce.thiskostha.repository.CustomerRepository;
import com.quickcommerce.thiskostha.repository.OrderRepository;

@Service
public class OrderService {
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private RestTemplate restTemplate;

    @Value("${myapp.api.key}")
    private String apiKey;
    
	public ResponseEntity<ResponseStructure<Order>> placeOrder(String phone, String method, String addressType,
			String deliveryInstructions, String specialInstructions) {
		Customer customer = customerRepo.findByPhone(phone);
        if (customer == null)
            throw new RuntimeException("Customer not found");
        
        Address deliveryAddress = null;
        for(Address a : customer.getAddresses()) {
     	   if(a.getAddressType().equals(addressType)) {
     		   deliveryAddress = a;
     	   }
        }
        
        if (deliveryAddress == null)
            throw new RuntimeException("Address not found");
        
        Restaurant restaurant = customer.getCart().getFirst().getItem().getRestaurant();
        
        double startLon = restaurant.getAddress().getLongitude();
        double startLat = restaurant.getAddress().getLatitude();
        double endLon = deliveryAddress.getLongitude();
        double endLat = deliveryAddress.getLatitude();

        String coordinates = startLon + "," + startLat + ";" + endLon + "," + endLat;
        String url = "https://us1.locationiq.com/v1/directions/driving/"
                + coordinates
                + "?key=" + apiKey
                + "&overview=false"
                + "&steps=false";
        
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> routes = (List<Map<String, Object>>) response.get("routes");

        Map<String, Object> firstRoute = routes.get(0);
        Double distanceMeters = ((Double) firstRoute.get("distance")).doubleValue();
        Long durationSeconds = ((Long) firstRoute.get("duration")).longValue();
        
        Double charges = null;
        if(distanceMeters < 2000.00) {
            charges = 0.00;
        }
        else if(distanceMeters < 10000.00 && distanceMeters > 2000.00) {
            charges = (distanceMeters / 1000) * 10;
        }
        else {
            throw new RuntimeException("order cant be delivered to this address");
        }

        List<CartItem> cartItems = customer.getCart();
        
        if (cartItems.isEmpty())
            throw new RuntimeException("Cart is empty");

        double total = cartItems.stream()
                .mapToDouble(item -> item.getItem().getPrice() * item.getQuantity())
                .sum();

        total += restaurant.getPackagefees();
        total += charges;
      
        Order order = new Order();
        order.setCost(total);
        order.setOrderTime(LocalDateTime.now());
        order.setDeliveryTime(LocalDateTime.now().plusMinutes((durationSeconds/60)+10));
        order.setRestaurant(restaurant);
        order.setCustomer(customer);
        order.setItems(cartItems);
        order.setDeliveryInstructions(deliveryInstructions);
        order.setSpecialinstructions(specialInstructions);
        order.setOtp(1000 + new java.util.Random().nextInt(9000));
        order.setDeliveryCharges(charges);
        order.setPickupAddress(restaurant.getAddress());
        order.setDeliveryAddress(deliveryAddress);
        order.setPackagingFee(restaurant.getPackagefees());
        
       
        customer.getOrders().add(order);
        customerRepo.save(customer);
        orderRepo.save(order);
        
        
        startAutoConfirmationTimer(order.getId());
        
        ResponseStructure<Order> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.ACCEPTED.value());
        rs.setMessage("Order placed. You have 3 minutes to cancel.");
        rs.setData(order);

        return new ResponseEntity<>(rs, HttpStatus.ACCEPTED);
    }

	
	public ResponseEntity<ResponseStructure<Order>> cancelOrder(Long orderId, String customerPhone) {
		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new RuntimeException("Order not found"));
		
		Customer customer = customerRepo.findByPhone(customerPhone);
		if (customer == null || !customer.getOrders().contains(order)) {
			throw new RuntimeException("Unauthorized: Order does not belong to this customer");
		}
		
	
		if (order.getDeliveryStatus() == OrderStatus.CONFIRMED) {
			throw new RuntimeException("Order already confirmed. Cannot cancel.");
		}
		
		
		order.setDeliveryStatus(OrderStatus.CANCELLED);
		orderRepo.save(order);
		
		
		customer.getCart().clear();
		customerRepo.save(customer);
		
		ResponseStructure<Order> rs = new ResponseStructure<>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("Order cancelled successfully.");
		rs.setData(order);
		
		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	
	private void startAutoConfirmationTimer(Long orderId) {
		Timer timer = new Timer();
		
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					Order order = orderRepo.findById(orderId)
							.orElse(null);
					
					if (order == null) {
						System.out.println("Order not found: " + orderId);
						return;
					}
					
					
					if (order.getDeliveryStatus() != OrderStatus.CANCELLED) {
						order.setDeliveryStatus(OrderStatus.CONFIRMED);
						orderRepo.save(order);
						
						
						sendOrderToRestaurant(order);
						
						System.out.println("Order " + orderId + " auto-confirmed and sent to restaurant");
					} else {
						System.out.println("Order " + orderId + " is cancelled, skipping restaurant send");
					}
				} catch (Exception e) {
					System.err.println("Error auto-confirming order " + orderId + ": " + e.getMessage());
					e.printStackTrace();
				}
			}
		}, 3 * 60 * 1000); // 3 minutes in milliseconds
	}

	
	
	
	private void sendOrderToRestaurant(Order order) {
		try {
		
			
			Map<String, Object> restaurantOrder = new java.util.HashMap<>();
			restaurantOrder.put("orderId", order.getId());
			restaurantOrder.put("restaurantId", order.getRestaurant().getId());
			restaurantOrder.put("restaurantName", order.getRestaurant().getName());
			restaurantOrder.put("items", order.getItems());
			restaurantOrder.put("totalAmount", order.getCost());
			restaurantOrder.put("customerPhone", order.getCustomer().getPhone());
			restaurantOrder.put("customerName", order.getCustomer().getName());
			restaurantOrder.put("deliveryAddress", order.getDeliveryAddress());
			restaurantOrder.put("status", "CONFIRMED");
			
			
			
			restTemplate.postForObject(
				"http://localhost:8081/restaurant/saveorder", 
				restaurantOrder,
				String.class
			);
			
			System.out.println("Order sent to restaurant successfully");
		} catch (Exception e) {
			System.err.println("Error sending order to restaurant: " + e.getMessage());
		}
	}
}