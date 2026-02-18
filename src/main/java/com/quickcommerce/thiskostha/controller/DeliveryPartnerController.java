

package com.quickcommerce.thiskostha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quickcommerce.thiskostha.dto.DeliveryPartnerDTO;
import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.DeliveryPartner;
import com.quickcommerce.thiskostha.service.DeliveryPartnerService;

@RestController
	@RequestMapping("/deliverypartner")
	public class DeliveryPartnerController {
		@Autowired
		private DeliveryPartnerService deliveryPartnerService;
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
}

}
	
