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
    public List<GroceryItem> getAll(){
        LOGGER.info("Retrieving all groceries from list");
        return groceryItemService.getGroceries();
    }

    /**
     * Add a new grocery item
     * @param groceryItem the item to be added
     * @return the grocery item
     */
    @PostMapping
    public GroceryItem addGrocery(@RequestBody GroceryItem groceryItem){
        LOGGER.info("Adding grocery item {} to list", groceryItem.getName());
        return groceryItemService.addGrocery(groceryItem);
    }

    /**
     * Increase the quantity by 1
     * @param id the item to be increased in quantity
     * @return the grocery item
     */
    @PutMapping("/{id}")
    public GroceryItem addQuantity(@PathVariable Integer id){
        LOGGER.info("Adding one quantity to grocery item {}", id);
        return groceryItemService.addQuantity(id);
    }

    @DeleteMapping("/{id}")
    public GroceryItem removeQuantity(@PathVariable Integer id) {
        LOGGER.info("Decreasing quantity of grocery item {}", id);
        return groceryItemService.removeQuantity(id);
    }

    @DeleteMapping("all/{id}")
    public ResponseEntity<?> removeGroceryById(@PathVariable Integer id) {
        LOGGER.info("Removing grocery item {} from list", id);
        GroceryItem groceryItem = groceryItemService.removeGroceryById(id);
        return createResponse(String.format("%s has been removed from list.", groceryItem.getName()));
    }

    @DeleteMapping
    public ResponseEntity<?> removeAll() {
        LOGGER.info("Removing all grocery items from list");
        groceryItemService.removeGroceries();
        return createResponse("All grocery items have been removed from list.");
    }

    private ResponseEntity<?> createResponse(String message){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", message);

        return ResponseEntity.ok(body);
    }

}
