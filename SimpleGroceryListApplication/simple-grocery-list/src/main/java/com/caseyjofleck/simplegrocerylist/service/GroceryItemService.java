package com.caseyjofleck.simplegrocerylist.service;

import com.caseyjofleck.simplegrocerylist.controller.GroceryItemController;
import com.caseyjofleck.simplegrocerylist.exception.GroceryItemException;
import com.caseyjofleck.simplegrocerylist.model.GroceryItem;
import com.caseyjofleck.simplegrocerylist.repository.GroceryItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroceryItemService {

    private final Logger LOGGER = LoggerFactory.getLogger(GroceryItemService.class);
    private final GroceryItemRepository groceryItemRepository;

    public GroceryItemService(GroceryItemRepository groceryItemRepository) {
        this.groceryItemRepository = groceryItemRepository;
    }

    public List<GroceryItem> getGroceries(){
        List<GroceryItem> gi = groceryItemRepository.findAll();
        if (gi.isEmpty()) throw new GroceryItemException("No groceries found.", HttpStatus.NOT_FOUND);
        return gi;
    }

    public GroceryItem add(GroceryItem groceryItem){
        if(groceryItem.getId() != null){
            checkIfItemExists(groceryItem.getId());
            groceryItem = groceryItemRepository.findById(groceryItem.getId()).get();
            groceryItem.setQuantity(groceryItem.getQuantity()+1);
            LOGGER.debug("Grocery item {} has been increased by 1", groceryItem.getId());
        }
        if(groceryItem.getQuantity() <= 0){
            LOGGER.error("The quantity of items must be greater than 0 for input item {}.", groceryItem.getName());
            throw new GroceryItemException("The quantity of items must be greater than 0.", HttpStatus.BAD_REQUEST);
        }
        groceryItem = groceryItemRepository.save(groceryItem);
        LOGGER.info("Grocery item {} has added", groceryItem.getId());
        return groceryItem;
    }

    public GroceryItem deleteById(Integer id) {
        checkIfItemExists(id);
        GroceryItem groceryItem = groceryItemRepository.findById(id).get();

        LOGGER.debug("Checking if decreasing quantity will remove item entirely");
        if(groceryItem.getQuantity() == 1){
            groceryItemRepository.deleteById(id);
            groceryItem.setQuantity(groceryItem.getQuantity()-1);
            LOGGER.info("Grocery item {} has been deleted", id);
        }else{
            groceryItem = groceryItemRepository.findById(groceryItem.getId()).get();
            groceryItem.setQuantity(groceryItem.getQuantity()-1);
            groceryItemRepository.save(groceryItem);
            LOGGER.info("Removed one quantity of grocery item {} from list", id);
        }

        return groceryItem;
    }

    public GroceryItem deleteAllById(Integer id) {
        checkIfItemExists(id);
        GroceryItem groceryItem = groceryItemRepository.findById(id).get();
        groceryItemRepository.deleteById(id);
        LOGGER.info("Grocery item {} has been deleted", id);
        return groceryItem;
    }

    public void deleteAll() {;
        groceryItemRepository.deleteAll();
        LOGGER.info("All grocery items have been removed");
    }

    private void checkIfItemExists(Integer id){
        if(!groceryItemRepository.existsById(id))
            throw new GroceryItemException("Operation cannot continue. Item not found in grocery list.", HttpStatus.NOT_FOUND);
    }

}
