package com.caseyjofleck.simplegrocerylist.service;

import com.caseyjofleck.simplegrocerylist.model.GroceryItem;
import com.caseyjofleck.simplegrocerylist.repository.GroceryItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryItemService {
    private final GroceryItemRepository groceryItemRepository;

    public GroceryItemService(GroceryItemRepository groceryItemRepository) {
        this.groceryItemRepository = groceryItemRepository;
    }

    public List<GroceryItem> getGroceries(){
        return groceryItemRepository.findAll();
    }


    public GroceryItem add(GroceryItem groceryItem){
        return groceryItemRepository.save(groceryItem);
    }

    public void deleteById(Integer id) {
       groceryItemRepository.deleteById(id);
    }

    public void deleteByName(String name) {
        groceryItemRepository.removeGroceryItemByName(name);
    }
}
