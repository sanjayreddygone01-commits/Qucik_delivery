package com.quickcommerce.thiskostha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickcommerce.thiskostha.entity.Restaurant;

@Repository

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

	Restaurant findByPhone(String phone);
	
	void deleteByPhone(String phone);

	List<Restaurant> findByAddress_City(String city);



}
