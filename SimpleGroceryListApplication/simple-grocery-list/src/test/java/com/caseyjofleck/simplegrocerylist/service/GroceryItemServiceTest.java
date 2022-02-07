package com.caseyjofleck.simplegrocerylist.service;

import com.caseyjofleck.simplegrocerylist.repository.GroceryItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class GroceryItemServiceTest {

    private GroceryItemRepository groceryItemRepository;
    private GroceryItemService groceryItemService;

    @BeforeEach
    void setUp() {
        groceryItemRepository = Mockito.mock(GroceryItemRepository.class);
        groceryItemService = new GroceryItemService(groceryItemRepository);
    }

    @Nested
    @DisplayName("GET all groceries")
    class getGroceries {
        @Test
        @DisplayName("Given no input data when groceries are found " +
                "then expect list of groceries.")
        void given_NoInputData_When_GroceriesFound_Then_ExpectListOfGroceries(){

        }
    }

    @Nested
    @DisplayName("Add or Update Grocery")
    class add {
        @Test
        @DisplayName("Given a grocery name when grocery is added " +
                "then expect grocery item.")
        void given_ValidGroceryName_When_GroceryAdded_Then_ExpectGroceryItem(){

        }

        @Test
        @DisplayName("Given a valid grocery id when quantity is increased " +
                "then expect increased quantity.")
        void given_ValidGroceryId_When_QuantityIncreased_Then_ExpectIncreasedQuantity(){

        }

    }

    @Nested
    @DisplayName("Remove Grocery")
    class deleteAllById {
        @Test
        @DisplayName("Given a valid grocery id when grocery is removed " +
                "then expect grocery item.")
        void given_ValidGroceryId_When_GroceryRemoved_Then_ExpectGroceryItem(){

        }
    }

    @Nested
    @DisplayName("Decrease quantity")
    class deleteById {
        @Test
        @DisplayName("Given a valid grocery id when one grocery quantity is removed " +
                "then expect grocery item.")
        void given_ValidGroceryId_When_OneQuantityRemoved_Then_ExpectGroceryItem(){

        }
    }

    @Nested
    @DisplayName("Remove All Groceries")
    class deleteAll {
        @Test
        @DisplayName("Given no input data when all groceries are removed " +
                "then expect nothing.")
        void given_NoInputData_When_AllGroceriesRemoved_Then_ExpectNothing(){

        }
    }
}