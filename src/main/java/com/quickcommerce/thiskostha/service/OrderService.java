package com.quickcommerce.thiskostha.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
    
	public ResponseEntity<ResponseStructure<Order>> placeOrder(String phone, String method, String addressType,String deliveryInstructions,String specialInstructions) {
		// TODO Auto-generated method stub
		Customer customer = customerRepo.findByPhone(phone);
        if (customer == null)
            throw new RuntimeException("Customer not found");
        
        
        Address deliveryAddress=null;
        for(Address a : customer.getAddresses()) {
     	   if(a.getAddressType().equals(addressType)) {
     		   deliveryAddress=a;
     	   }
        }
        
        Restaurant restaurant= customer.getCart().getFirst().getItem().getRestaurant();
        
        double startLon =restaurant.getAddress().getLongitude();
        double startLat = restaurant.getAddress().getLatitude();
        double endLon =deliveryAddress.getLongitude() ;
        double endLat = deliveryAddress.getLatitude();

        String coordinates = startLon + "," + startLat + ";" + endLon + "," + endLat;
        String url="https://us1.locationiq.com/v1/directions/driving/"
                + coordinates
                + "?key=" + apiKey
                + "&overview=false"
                + "&steps=false";
        
        Map<String, Object> response =
                restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> routes =
                (List<Map<String, Object>>) response.get("routes");

       

            Map<String, Object> firstRoute = routes.get(0);

           
            Double distanceMeters =
                    ((Double) firstRoute.get("distance")).doubleValue();
            
           

            
            Long durationSeconds =
                    ((Long) firstRoute.get("duration")).longValue();
            
            Double charges=null;
            if(distanceMeters<2000.00) {
            	charges=0.00;
            }
            else if(distanceMeters<10000.00&&distanceMeters>2000.00) {
            charges=(distanceMeters/1000)*10;
            }
            else 
            {
            	throw new RuntimeException("order cant be delivered to this address");
            }

        
        

        List<CartItem> cartItems = customer.getCart();
        
        if (cartItems.isEmpty())
            throw new RuntimeException("Cart is empty");

        
        double total = cartItems.stream()
                .mapToDouble(item -> item.getItem().getPrice() * item.getQuantity())
                .sum();

        
      
        total+=restaurant.getPackagefees();
        total+=charges;
        // Create order
        Order order = new Order();
        order.setStatus("pending");
        order.setCost(total);
        if (method == "COD")
            order.setPaymentStatus("PENDING");
        else
            order.setPaymentStatus("COMPLETED");
        order.setOrderTime(LocalDateTime.now());
        order.setDeliverTime(LocalDateTime.now().plusMinutes((durationSeconds/60)+10));
        order.setDeliveryStatus("PENDING");
        order.setRestaurant(restaurant);
        order.setCustomer(customer);
        order.setItems(cartItems);
        order.setDeliveryInstructions(deliveryInstructions);
        order.setSpecialinstructions(specialInstructions);
        order.setOtp(1000 + new java.util.Random().nextInt(9000));
        order.setDeliveryCharges(charges);
        order.setPickupAddress(restaurant.getAddress());
        order.setDeliveryAddress(deliveryAddress);
        
       customer.getOrders().add(order);
       customerRepo.save(customer);
       orderRepo.save(order);
        
        ResponseStructure<Order> rs = new ResponseStructure<>();
        rs.setStatuscode(HttpStatus.ACCEPTED.value());
        rs.setMessage("Order placed successfully");
        rs.setData(order);

        return ResponseEntity.ok(rs);
    
	}

}
