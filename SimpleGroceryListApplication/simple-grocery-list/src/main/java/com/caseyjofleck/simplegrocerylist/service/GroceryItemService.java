package com.caseyjofleck.simplegrocerylist.service;

import com.caseyjofleck.simplegrocerylist.exception.GroceryItemException;
import com.caseyjofleck.simplegrocerylist.model.GroceryItem;
import com.caseyjofleck.simplegrocerylist.repository.GroceryItemRepository;
import org.apache.commons.lang3.StringUtils;
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
        if (gi.isEmpty()){
            LOGGER.error("No groceries found.");
            throw new GroceryItemException("No groceries found.", HttpStatus.NOT_FOUND);
        }
        return gi;
    }

    public GroceryItem addGrocery(GroceryItem groceryItem){
        groceryItem.setId(null);
        if(StringUtils.isBlank(groceryItem.getName())){
            LOGGER.error("Grocery item name must not be blank when adding new item.");
            throw new GroceryItemException("Grocery item name must not be blank when adding new item.", HttpStatus.BAD_REQUEST);
        }
        if(groceryItem.getQuantity() <= 0){
            LOGGER.error("The quantity of items must be greater than 0 for input item {}.", groceryItem.getName());
            throw new GroceryItemException("The quantity of items must be greater than 0.", HttpStatus.BAD_REQUEST);
        }
        groceryItem = groceryItemRepository.save(groceryItem);
        LOGGER.info("Grocery item {} has added", groceryItem.getId());
        return groceryItem;
    }

    public GroceryItem addQuantity(Integer id){
        checkIfItemExists(id);
        GroceryItem groceryItem = groceryItemRepository.findById(id).get();
        groceryItem.setQuantity(groceryItem.getQuantity()+1);
        LOGGER.debug("Grocery item {} has been increased by 1", groceryItem.getId());
        groceryItem = groceryItemRepository.save(groceryItem);
        LOGGER.info("Grocery item {} has added", groceryItem.getId());
        return groceryItem;
    }

    public GroceryItem removeQuantity(Integer id) {
        checkIfItemExists(id);
        GroceryItem groceryItem = groceryItemRepository.findById(id).get();
        LOGGER.debug("Grocery item {}[id={}] quantity is currently {}", groceryItem.getName(), groceryItem.getId(), groceryItem.getQuantity());

        LOGGER.debug("Checking if decreasing quantity will remove item entirely");
        if(groceryItem.getQuantity() == 1){
            LOGGER.debug("Removing grocery item {}[id={}] from list entirely", groceryItem.getName(), groceryItem.getId());
            groceryItemRepository.deleteById(id);
            groceryItem.setQuantity(groceryItem.getQuantity()-1);
            LOGGER.info("Grocery item {}[id={}] has been deleted", groceryItem.getName(), groceryItem.getId());
        }else{
            groceryItem = groceryItemRepository.findById(groceryItem.getId()).get();
            groceryItem.setQuantity(groceryItem.getQuantity()-1);
            groceryItemRepository.save(groceryItem);
            LOGGER.info("Removed one quantity of grocery item {}[id={}] from list", groceryItem.getName(), groceryItem.getId());
            LOGGER.debug("Grocery item {}[id={}] quantity is now {}", groceryItem.getName(), groceryItem.getId(), groceryItem.getQuantity());
        }

        return groceryItem;
    }

    public GroceryItem removeGroceryById(Integer id) {
        checkIfItemExists(id);
        GroceryItem groceryItem = groceryItemRepository.findById(id).get();
        groceryItemRepository.deleteById(id);
        LOGGER.info("Grocery item {} has been deleted", id);
        return groceryItem;
    }

    public void removeGroceries() {
        if(groceryItemRepository.count() == 0){
            LOGGER.error("No groceries found in list.");
            throw new GroceryItemException("No groceries found.", HttpStatus.NOT_FOUND);
        }
        groceryItemRepository.deleteAll();
        LOGGER.info("All grocery items have been removed");
    }

    private void checkIfItemExists(Integer id){
        if(!groceryItemRepository.existsById(id)){
            LOGGER.error("Operation cannot continue. Item not found in grocery list.");
            throw new GroceryItemException("Operation cannot continue. Item not found in grocery list.", HttpStatus.NOT_FOUND);
        }
    }

}
