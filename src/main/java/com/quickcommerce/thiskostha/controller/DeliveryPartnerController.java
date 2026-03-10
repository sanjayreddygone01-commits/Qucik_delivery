

package com.quickcommerce.thiskostha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickcommerce.thiskostha.dto.DeliveryPartnerDTO;
import com.quickcommerce.thiskostha.dto.LocationCordinates;
import com.quickcommerce.thiskostha.dto.OrderResponse;
import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.DeliveryPartner;
import com.quickcommerce.thiskostha.service.DeliveryPartnerService;
import com.quickcommerce.thiskostha.service.RedisService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
	@RequestMapping("/deliverypartner")
	public class DeliveryPartnerController {
		@Autowired
		private DeliveryPartnerService deliveryPartnerService;

		 @Autowired
		    private RedisService redisService;
		@PostMapping("/register")
		public ResponseEntity<ResponseStructure<DeliveryPartner>> register(@RequestBody DeliveryPartnerDTO deliveryPartnerdto){
			return deliveryPartnerService.register(deliveryPartnerdto); 
		}
		@GetMapping("/finddeliverypartner/{phoneno}")
		public ResponseEntity<ResponseStructure<DeliveryPartner>> findDeliveryPartner(@RequestParam String phone){
			return deliveryPartnerService.findDeliveryPartner(phone);
		}
		@DeleteMapping("/deletedeliverypartner/{phoneno}")
		public ResponseEntity<ResponseStructure<DeliveryPartner>> deleteDeliveryPartner(@RequestParam String phone){
			return deliveryPartnerService.deleteDeliveryPartner(phone);	
		}

	    @PostMapping("/updateDpLoc")
	    public ResponseEntity<String> updateDpLoc(@RequestParam Integer partnerid, @RequestParam double latitude, double longitude) {
	        String s = redisService.updateDpLoc(partnerid, latitude, longitude);
	        return new ResponseEntity<>(s, HttpStatus.OK);
	    }

	    @PostMapping("/acceptorder")
	    public ResponseEntity<ResponseStructure<OrderResponse>> acceptorder(@RequestParam Long orderid, @RequestParam Long partnerid) {
	    	return  deliveryPartnerService.acceptorder(orderid, partnerid);

	        
	    }
	    
	    @GetMapping("/getDirectionstoRestaurant")
	    public void getDirections(@RequestParam Long orderid,@RequestBody LocationCordinates cordinates,HttpServletResponse response) {
	    	 deliveryPartnerService.getDirections(orderid,cordinates,response);
	    }
	    
	    @PatchMapping("/pickedupOrder")
	    public ResponseEntity<ResponseStructure<String>> pickupOrder(@RequestParam Long orderid) {
	    	 return deliveryPartnerService.statusUpdateTopickedup(orderid);
	    }
	    
	    @PatchMapping("/deliveredOrder")
	    public ResponseEntity<ResponseStructure<String>> dileverdOrder(@RequestParam Long orderid,Integer otp) {
	    	 return deliveryPartnerService.deliveredOrder(orderid,otp);
	    }
	    
	    
}

	
