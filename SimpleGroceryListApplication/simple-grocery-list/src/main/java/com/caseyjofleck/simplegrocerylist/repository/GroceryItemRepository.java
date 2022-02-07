package com.caseyjofleck.simplegrocerylist.repository;

import com.caseyjofleck.simplegrocerylist.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroceryItemRepository extends JpaRepository<GroceryItem, Integer> {
}
