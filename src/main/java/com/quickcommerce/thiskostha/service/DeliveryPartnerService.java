package com.quickcommerce.thiskostha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.quickcommerce.thiskostha.dto.DeliveryPartnerDTO;
import com.quickcommerce.thiskostha.dto.ResponseStructure;
import com.quickcommerce.thiskostha.entity.DeliveryPartner;
import com.quickcommerce.thiskostha.repository.DeliveryPartnerRepository;
@Service
public class DeliveryPartnerService {
@Autowired
	private DeliveryPartnerRepository deliveryPartnerRepo;
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
}
