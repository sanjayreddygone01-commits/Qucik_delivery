package com.quickcommerce.thiskostha.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quickcommerce.thiskostha.dto.OrderConsent;
import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.Address;
import com.quickcommerce.thiskostha.entity.CartItem;
import com.quickcommerce.thiskostha.entity.Customer;
import com.quickcommerce.thiskostha.entity.Order;
import com.quickcommerce.thiskostha.entity.OrderStatus;
import com.quickcommerce.thiskostha.entity.Payment;
import com.quickcommerce.thiskostha.entity.PaymentStatus;
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
    
	public ResponseEntity<ResponseStructure<OrderConsent>> placeOrder(String phone, String method, String addressType,
			String deliveryInstructions, String specialRequests) {
		
		  Customer customer = customerRepo.findByPhone(phone);
		  if(customer==null)throw new RuntimeException("customer not found");

	        if(customer.getCart().isEmpty()){
	            throw new RuntimeException("Cart is empty");
	        }
	       
	        Restaurant restaurant = customer.getCart().get(0).getItem().getRestaurant();

	           Address pickupAddress=restaurant.getAddress();
	          
	         Address deliveryAddress=null;
	         for(Address a:customer.getAddresses()){
	             if(a.getAddressType().equals(addressType)){
	                 deliveryAddress=a;
	             }
	         }
	       

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
	        Double durationSeconds = ((Double) firstRoute.get("duration")).doubleValue();
	        
		      
	         Double deliverycharges = null;
	        if(distanceMeters < 2000.00) {
	            deliverycharges = 0.00;
	        }
	        else if(distanceMeters < 10000.00 && distanceMeters > 2000.00) {
	            deliverycharges = (distanceMeters / 1000) * 10;
	        }
	        else {
	            throw new RuntimeException("order cant be delivered to this address");
	        }
	      
	         deliverycharges=(double) Math.round(deliverycharges);
	         
	         double cost=0;

	         for( CartItem c:customer.getCart()){

	             cost=cost+(c.getItem().getPrice()*c.getQuantity());
	                     }
	         Order order=new Order();
	         
		        order.setCustomer(customer);
		        order.setDeliveryStatus(OrderStatus.PENDING);
	
		        order.setPickupAddress(pickupAddress);
		        order.setDeliveryAddress(deliveryAddress);
		
		        order.setSpecialinstructions(specialRequests);
	            order.setDeliveryInstructions(deliveryInstructions);
	        
	            order.setDeliveryPartner(null);
	            order.setOrderTime(LocalDateTime.now());
	        
	            order.setDistance(distanceMeters/1000);
	            
	            order.setDeliveryCharges(deliverycharges);
                order.setTax(cost*(18/100));
	            order.setPlatformFee(5.0);
//	       

	            order.setCost(cost);

	           order.setPackagingFee(restaurant.getPackagefees());
	           double tax=cost*(8/100);
	           
	           double totalCost= (cost + deliverycharges+ restaurant.getPackagefees() + tax );

		        order.setTotalCost(totalCost+5.00);
	
	          Payment payment=new Payment();

	          payment.setMethod(method);
	          if(method.equalsIgnoreCase("COD")){
	              payment.setStatus(PaymentStatus.PENDING);
	          }else{
	              payment.setStatus(PaymentStatus.COMPLETED);
	          }
	          order.setPayment(payment);
	           payment.setOrder(order);


	        SecureRandom random=new SecureRandom();
	        int otp= 1000 + random.nextInt(9000);
	        order.setOtp(otp);

	        Order orderinitiated=  orderRepo.save(order);
	        
	        

	           
	        double packagingFees= order.getPackagingFee();
	        double gst = order.getTax();
	        double platformFees= order.getPlatformFee();
	        double TotalCost= order.getTotalCost();

	       
	        OrderConsent dto = new OrderConsent();

	        dto.setOrderId(orderinitiated.getId());
	        dto.setRestaurantName(restaurant.getName());
	        dto.setItemCost(cost);
	        dto.setDeliveryCharges(deliverycharges);
	        dto.setPackagingFees(packagingFees);
	        dto.setTax(tax);
	        dto.setPlatformFees(platformFees);
	        dto.setTotalCost(totalCost);
	        dto.setDistance(distanceMeters);

	        ResponseStructure<OrderConsent> rs = new ResponseStructure<>();
	        rs.setData(dto);
	        rs.setMessage("Order Initiated,Do you wish to Confirm Order");
	        rs.setStatuscode(200);
	        return new ResponseEntity<ResponseStructure<OrderConsent>>(rs, HttpStatus.OK);
	    }

	

	    public ResponseEntity<ResponseStructure<Order>> denyPlacingOrder(Long orderid) {
	        Order order = orderRepo.findById(orderid)
	        		.orElseThrow( () ->  new RuntimeException("order not found "));
	        
	        order.setDeliveryStatus(OrderStatus.CANCELLED);
	        orderRepo.save(order);
	        ResponseStructure<Order> rs=new ResponseStructure<>();
	        
	        rs.setData(order);
	        rs.setMessage("Order Cancelled Successfully");
	        rs.setStatuscode(200);
	        return  new ResponseEntity<ResponseStructure<Order>>(rs,HttpStatus.OK);

    }

	    public ResponseEntity<ResponseStructure<Order>> confirmPlacingOrder(Long orderid){
	    	 Order order = orderRepo.findById(orderid).orElseThrow(() -> new RuntimeException("Order not found with this id"));
	      
	        Customer customer = order.getCustomer();
	        Restaurant restaurant = customer.getCart().get(0).getItem().getRestaurant();
	        order.setRestaurant(restaurant);
	        
	        order.setDeliveryStatus(OrderStatus.CONFIRMED);
	        customer.getCart().clear();
	       orderRepo.save(order);
	       ResponseStructure<Order> rs=new ResponseStructure<>();
	       rs.setData(order);
	       rs.setMessage("Order Placed Successfully");
	       rs.setStatuscode(200);
	       return  new ResponseEntity<ResponseStructure<Order>>(rs,HttpStatus.OK);
	    }


	    @Autowired
	    private CouponRedemptionService couponRedemptionService;

	    
	    public ResponseEntity<ResponseStructure<Order>> placeOrderWithCoupon(
	        Long orderId,
	         Integer couponId) {
	        
	        if (couponId != null) {
	            // Apply coupon and get discount
	            ResponseEntity<ResponseStructure<Double>> discountResponse = 
	                couponRedemptionService.redeemCoupon(couponId, orderId);
	            
	            if (discountResponse.getStatusCode() == HttpStatus.OK) {
	                Double discountAmount = discountResponse.getBody().getData();
	                // Order is already updated with discounted amount
	                Order order = orderRepo.findById(orderId).get();
	                // Proceed with payment on the discounted amount
	            }
	        }
	        
	        Order finalOrder = orderRepo.findById(orderId).get();
	        ResponseStructure<Order> rs = new ResponseStructure<>();
	        rs.setStatuscode(HttpStatus.OK.value());
	        rs.setMessage("Order placed with discount applied");
	        rs.setData(finalOrder);
	        
	        return new ResponseEntity<>(rs, HttpStatus.OK);
	    }
	
	

	
}