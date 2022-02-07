package com.caseyjofleck.simplegrocerylist.controller;

import com.caseyjofleck.simplegrocerylist.model.GroceryItem;
import com.caseyjofleck.simplegrocerylist.service.GroceryItemService;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("groceries")
public class GroceryItemController {

    private final Logger LOGGER = LoggerFactory.getLogger(GroceryItemController.class);
    private final GroceryItemService groceryItemService;

    public GroceryItemController(GroceryItemService groceryItemService) {
        this.groceryItemService = groceryItemService;
    }

    @GetMapping
    public List<GroceryItem> all(){
        LOGGER.info("Retrieving all groceries from list");
        return groceryItemService.getGroceries();
    }

    /**
     * Add a new item or increase the quantity by 1. If increasing quantity, must include id of item
     * @param groceryItem the item to be added or increased in quantity
     * @return the grocery item
     */
    @PutMapping
    public GroceryItem addGrocery(@RequestBody GroceryItem groceryItem){
        LOGGER.info("Adding grocery item {} to list", groceryItem.getName());
        return groceryItemService.add(groceryItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Integer id) {
        LOGGER.info("Decreasing quantity of grocery item {}", id);
        GroceryItem groceryItem = groceryItemService.deleteById(id);
        return createResponse(
                String.format("One item of %s has been removed. %s",
                        groceryItem.getName(),
                        groceryItem.getQuantity() == 0 ?
                                String.format("There are no longer %s in this list.", groceryItem.getName()) :
                                String.format("Quantity of %s is now %d.", groceryItem.getName(),
                                        groceryItem.getQuantity())));
    }

    @DeleteMapping("all/{id}")
    public ResponseEntity<?> deleteOneById(@PathVariable Integer id) {
        LOGGER.info("Removing grocery item {} from list", id);
        GroceryItem groceryItem = groceryItemService.deleteAllById(id);
        return createResponse(String.format("Grocery item %s has been removed from list.", groceryItem.getName()));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAll() {
        LOGGER.info("Removing all grocery items from list");
        groceryItemService.deleteAll();
        return createResponse("All grocery items have been removed from list.");
    }

    private ResponseEntity<?> createResponse(String message){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);

        return ResponseEntity.ok(body);
    }

}
