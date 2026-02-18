package com.quickcommerce.thiskostha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickcommerce.thiskostha.entity.DeliveryPartner;
@Repository
public interface DeliveryPartnerRepository extends JpaRepository<DeliveryPartner,Long>{
	

DeliveryPartner findByPhone(String phone);


	void deleteByPhone(String phone);
}
