package com.quickcommerce.thiskostha.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quickcommerce.thiskostha.dto.DeliveryPartnerDTO;
import com.quickcommerce.thiskostha.dto.LocationCordinates;
import com.quickcommerce.thiskostha.dto.OrderResponse;
import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.DeliveryPartner;
import com.quickcommerce.thiskostha.entity.Order;
import com.quickcommerce.thiskostha.entity.OrderStatus;
import com.quickcommerce.thiskostha.repository.DeliveryPartnerRepository;
import com.quickcommerce.thiskostha.repository.OrderRepository;

import jakarta.servlet.http.HttpServletResponse;
@Service
public class DeliveryPartnerService {
@Autowired
	private DeliveryPartnerRepository deliveryPartnerRepo;
@Autowired 
private OrderRepository orderRepository;
@Autowired
private RedisTemplate<String, String> redisTemplate;

	public ResponseEntity<ResponseStructure<DeliveryPartner>> register(DeliveryPartnerDTO deliveryPartnerdto) {
		DeliveryPartner deliveryPartner=new DeliveryPartner();
		deliveryPartner.setName(deliveryPartnerdto.getName());
		deliveryPartner.setPhone(deliveryPartnerdto.getPhone());
		deliveryPartner.setEmail(deliveryPartnerdto.getEmail());
		deliveryPartner.setVehicleNo(deliveryPartnerdto.getVehicleno());
		
		deliveryPartnerRepo.save(deliveryPartner);
		ResponseStructure<DeliveryPartner> rs=new ResponseStructure<DeliveryPartner>();
		rs.setStatuscode(HttpStatus.CREATED.value());
		rs.setMessage("Delivery Partner saved");
		rs.setData(deliveryPartner);
		
		return new ResponseEntity<ResponseStructure<DeliveryPartner>>(rs,HttpStatus.CREATED);
	}
	public ResponseEntity<ResponseStructure<DeliveryPartner>> deleteDeliveryPartner(String phone) {
		deliveryPartnerRepo.deleteByPhone(phone);
		ResponseStructure<DeliveryPartner> rs = new ResponseStructure<DeliveryPartner>();
		rs.setStatuscode(HttpStatus.OK.value());
		rs.setMessage("deliveryPartner deleted successfully");
		rs.setData(null);
		
		return new ResponseEntity<ResponseStructure<DeliveryPartner>>(rs,HttpStatus.OK);
	}
	public ResponseEntity<ResponseStructure<DeliveryPartner>> findDeliveryPartner(String phone) {
		DeliveryPartner deliveryPartner =deliveryPartnerRepo.findByPhone(phone);
		if(deliveryPartner==null) {throw new RuntimeException();}
		ResponseStructure<DeliveryPartner> rs = new ResponseStructure<DeliveryPartner>();
		rs.setStatuscode(HttpStatus.FOUND.value());
		rs.setMessage("deliveryPartner fteched successfully");
		rs.setData(deliveryPartner);
		
		return new ResponseEntity<ResponseStructure<DeliveryPartner>>(rs,HttpStatus.FOUND);
	} 
	
	 public ResponseEntity<ResponseStructure<OrderResponse>> acceptorder( Long orderid, Long partnerid) {
	        Order order = orderRepository.findById(orderid).orElseThrow(() -> new RuntimeException("Order not found"));
	        DeliveryPartner deliveryPartner = deliveryPartnerRepo.findById(partnerid).orElseThrow(()
	                -> new RuntimeException("partner not found"));


	        String lockKey = "order_lock" + orderid;
	        Boolean locked = redisTemplate.opsForValue().setIfAbsent(lockKey, partnerid.toString());
	        if (Boolean.TRUE.equals(locked)) {
	            order.setDeliveryPartner(deliveryPartner);
	            orderRepository.save(order);
	            redisTemplate.delete("order:" + orderid);
	            OrderResponse orderresponse=new OrderResponse();
	            orderresponse.setId(order.getId());
	            orderresponse.setDistance(order.getDistance());
	            orderresponse.setDeliveryCharges(order.getDeliveryCharges());
	            orderresponse.setPickupAddress(order.getPickupAddress());
	            orderresponse.setDeliveryAddress(order.getDeliveryAddress());
	            orderresponse.setTotalCost(order.getTotalCost());
	            
	            ResponseStructure<OrderResponse> rs=new ResponseStructure<OrderResponse>();
	    		rs.setStatuscode(HttpStatus.ACCEPTED.value());
	    		rs.setMessage("accepted order");
	    		rs.setData(orderresponse);
	    		
	    		return new ResponseEntity<ResponseStructure<OrderResponse>>(rs,HttpStatus.ACCEPTED);
	            

	           
	        }
	        else {
	        throw new RuntimeException("already locked");}
	    }
	 
	 public void getDirections(Long orderid,LocationCordinates cordinates,HttpServletResponse response) {
		 
		  Order order = orderRepository.findById(orderid).orElseThrow(() -> new RuntimeException("Order not found"));
		  double dplat = cordinates.getLatitude();
		  double dplongi = cordinates.getLongitude();
		  //restaurant cordinates
		  double restlat = order.getRestaurant().getAddress().getLatitude();
	      double restlongi = order.getRestaurant().getAddress().getLongitude();

		   
		 String getdir = "https://www.google.com/maps/dir/?api=1&origin=" + dplat + "," + dplongi + "&destination="
			        + restlat + "," + restlongi + "&travelmode=driving";
		 try {
			response.sendRedirect(getdir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 public ResponseEntity<ResponseStructure<String>> statusUpdateTopickedup(Long orderid) {
		 Order order = orderRepository.findById(orderid).orElseThrow(() -> new RuntimeException("Order not found"));
		 order.setDeliveryStatus(OrderStatus.PICKEDUP);
		 orderRepository.save(order);
		 ResponseStructure<String> rs=new ResponseStructure<String>();
 		rs.setStatuscode(HttpStatus.ACCEPTED.value());
 		rs.setMessage("accepted order");
 		rs.setData("pickedup");
 		
 		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.ACCEPTED);
         
		 
	 }
	 public ResponseEntity<ResponseStructure<String>> deliveredOrder(Long orderid, Integer otp) {
		 Order order = orderRepository.findById(orderid).orElseThrow(() -> new RuntimeException("Order not found"));
		 if(order.getOtp()==otp) {
			 order.setDeliveryStatus(OrderStatus.DELIVERED);
			 ResponseStructure<String> rs=new ResponseStructure<String>();
		 		rs.setStatuscode(HttpStatus.ACCEPTED.value());
		 		rs.setMessage("deliverred order");
		 		rs.setData("delivered");
		 		
		 		return new ResponseEntity<ResponseStructure<String>>(rs,HttpStatus.ACCEPTED);
			
		 }else {
			 throw new RuntimeException("invalid otp");
		 }
	 }
	 public void getDirectionstoCustomer(Long orderid, HttpServletResponse response) {
		 Order order = orderRepository.findById(orderid).orElseThrow(() -> new RuntimeException("Order not found"));
		 double dplat = order.getRestaurant().getAddress().getLatitude();
		  double dplongi =  order.getRestaurant().getAddress().getLongitude();
		  //restaurant cordinates
		  double restlat = order.getDeliveryAddress().getLatitude();
	      double restlongi = order.getDeliveryAddress().getLongitude();
	      String getdir = "https://www.google.com/maps/dir/?api=1&origin=" + dplat + "," + dplongi + "&destination="
			        + restlat + "," + restlongi + "&travelmode=driving";
		 try {
			response.sendRedirect(getdir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 

		}

