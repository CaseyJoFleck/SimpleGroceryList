package com.caseyjofleck.simplegrocerylist.controller;

import com.caseyjofleck.simplegrocerylist.controller.ControllerAdvice.GroceryControllerAdvice;
import com.caseyjofleck.simplegrocerylist.service.GroceryItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

class GroceryItemControllerTest {

    private GroceryItemService groceryItemService;

    private GroceryItemController groceryItemController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        groceryItemService = Mockito.mock(GroceryItemService.class);
        groceryItemController = new GroceryItemController(groceryItemService);
        mockMvc = MockMvcBuilders.standaloneSetup(groceryItemController)
                .setControllerAdvice(GroceryControllerAdvice.class).build();
    }

    @Nested
    @DisplayName("GET all groceries")
    class all {
        @Test
        @DisplayName("Given no input data when groceries are found " +
                "then expect OK HTTP Status Code.")
        void given_NoInputData_When_GroceriesFound_Then_ExpectOk(){

        }
    }

    @Nested
    @DisplayName("Add or Update Grocery")
    class add {
        @Test
        @DisplayName("Given a grocery name when grocery is added " +
                "then expect OK HTTP Status Code.")
        void given_ValidGroceryName_When_GroceryAdded_Then_ExpectOk(){

        }

        @Test
        @DisplayName("Given a valid grocery id when quantity is increased " +
                "then expect OK HTTP Status Code.")
        void given_ValidGroceryId_When_QuantityIncreased_Then_ExpectOk(){

        }

    }

    @Nested
    @DisplayName("Remove Grocery")
    class deleteById {
        @Test
        @DisplayName("Given a valid grocery id when grocery is removed " +
                "then expect OK HTTP Status Code.")
        void given_ValidGroceryId_When_GroceryRemoved_Then_ExpectOk(){

        }
    }

    @Nested
    @DisplayName("Remove 1 Grocery Quantity")
    class deleteOneById {
        @Test
        @DisplayName("Given a valid grocery id when grocery quantity is decreased " +
                "then expect OK HTTP Status Code.")
        void given_ValidGroceryId_When_QuantityDecreased_Then_ExpectOk(){

        }
    }

    @Nested
    @DisplayName("Remove All Groceries")
    class deleteAll {
        @Test
        @DisplayName("Given no input data when all groceries are removed " +
                "then expect OK HTTP Status Code.")
        void given_NoInputData_When_AllGroceriesRemoved_Then_ExpectOk(){

        }
    }

}