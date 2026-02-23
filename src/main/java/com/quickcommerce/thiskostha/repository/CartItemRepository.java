package com.quickcommerce.thiskostha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickcommerce.thiskostha.entity.CartItem;
import com.quickcommerce.thiskostha.entity.Customer;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long>{

	List<CartItem> findByCustomer(Customer customer);

}
