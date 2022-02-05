package com.caseyjofleck.simplegrocerylist.controller;

import com.caseyjofleck.simplegrocerylist.model.GroceryItem;
import com.caseyjofleck.simplegrocerylist.service.GroceryItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("groceries")
public class GroceryItemController {

    private final GroceryItemService groceryItemService;

    public GroceryItemController(GroceryItemService groceryItemService) {
        this.groceryItemService = groceryItemService;
    }

    @GetMapping
    public List<GroceryItem> all(){
        return groceryItemService.getGroceries();
    }

    @PostMapping
    public GroceryItem addGrocery(@RequestBody GroceryItem groceryItem){
        return groceryItemService.add(groceryItem);
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id) {
        groceryItemService.deleteById(id);
    }

    @DeleteMapping("/{name}")
    void deleteById(@PathVariable String name) {
        groceryItemService.deleteByName(name);
    }

}
