package com.quickcommerce.thiskostha.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.quickcommerce.thiskostha.entity.Item;
@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{

	List<Item> findByNameContainingIgnoreCase(String searchKey);

}
