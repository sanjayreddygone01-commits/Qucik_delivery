package com.quickcommerce.thiskostha.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickcommerce.thiskostha.entity.CartItem;
@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long>{

//	List<CartItem> findByCustomer(Customer customer);

}
