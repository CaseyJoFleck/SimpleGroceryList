package com.caseyjofleck.simplegrocerylist.service;

import com.caseyjofleck.simplegrocerylist.exception.GroceryItemException;
import com.caseyjofleck.simplegrocerylist.model.GroceryItem;
import com.caseyjofleck.simplegrocerylist.repository.GroceryItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GroceryItemServiceTest {

    private GroceryItemRepository groceryItemRepository;
    private GroceryItemService groceryItemService;
    private GroceryItem groceryItem;

    @BeforeEach
    void setUp() {
        groceryItemRepository = Mockito.mock(GroceryItemRepository.class);
        groceryItemService = new GroceryItemService(groceryItemRepository);

        groceryItem = new GroceryItem();
        groceryItem.setQuantity(1);
        groceryItem.setName("apple");
        groceryItem.setId(1);
    }

    @Nested
    @DisplayName("GET All Groceries")
    class getGroceries {
        @Test
        @DisplayName("Given no input data when groceries are found " +
                "then expect list of groceries.")
        void given_NoInputData_When_GroceriesFound_Then_ExpectListOfGroceries(){
            //given
            when(groceryItemRepository.findAll()).thenReturn(List.of(groceryItem));

            //when
            List<GroceryItem> retrievedGroceries = groceryItemService.getGroceries();

            //then
            assertEquals(List.of(groceryItem), retrievedGroceries);

        }

        @Test
        @DisplayName("Given no input data when groceries are not found " +
                "then expect exception.")
        void given_NoInputData_When_NoGroceriesFound_Then_ExpectException(){
            //when
            when(groceryItemRepository.findAll()).thenReturn(List.of());

            //then
            assertThrows(GroceryItemException.class,
                    () -> groceryItemService.getGroceries());
        }
    }

    @Nested
    @DisplayName("Add Grocery")
    class addGrocery {
        @Test
        @DisplayName("Given a grocery name when grocery is added " +
                "then expect grocery item.")
        void given_ValidGroceryName_When_GroceryAdded_Then_ExpectGroceryItem(){
            //given
            when(groceryItemRepository.save(Mockito.any())).thenReturn(groceryItem);

            //when
            GroceryItem retrievedGrocery = groceryItemService.addGrocery(groceryItem);

            //then
            assertEquals(groceryItem, retrievedGrocery);
        }

        @Test
        @DisplayName("Given no grocery name when grocery is added " +
                "then expect exception.")
        void given_NoGroceryName_When_GroceryAdded_Then_ExpectException(){
            //given
            groceryItem.setName(null);

            //then
            assertThrows(GroceryItemException.class,
                    () -> groceryItemService.addGrocery(groceryItem));
        }

        @Test
        @DisplayName("Given invalid quantity when grocery is added " +
                "then expect exception.")
        void given_InvalidQuantity_When_GroceryAdded_Then_ExpectException(){
            //given
            groceryItem.setQuantity(-1);

            //then
            assertThrows(GroceryItemException.class,
                    () -> groceryItemService.addGrocery(groceryItem));
        }
    }

    @Nested
    @DisplayName("Add Quantity")
    class addQuantity {
        @Test
        @DisplayName("Given a valid grocery id when quantity is increased " +
                "then expect grocery item.")
        void given_ValidGroceryId_When_QuantityIncreased_Then_ExpectGroceryItem(){
            //given
            when(groceryItemRepository.existsById(Mockito.anyInt())).thenReturn(true);
            when(groceryItemRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(groceryItem));
            when(groceryItemRepository.save(groceryItem)).thenReturn(groceryItem);

            //when
            GroceryItem retrievedGrocery = groceryItemService.addQuantity(Mockito.anyInt());

            //then
            assertEquals(groceryItem, retrievedGrocery);
        }

        @Test
        @DisplayName("Given invalid grocery item id when quantity is increased " +
                "then expect exception.")
        void given_InvalidGroceryId_When_GroceryAdded_Then_ExpectException(){
            //given
            when(groceryItemRepository.existsById(Mockito.anyInt())).thenReturn(false);

            //then
            assertThrows(GroceryItemException.class,
                    () -> groceryItemService.addQuantity(Mockito.anyInt()));
        }
    }

    @Nested
    @DisplayName("Remove Grocery")
    class removeGroceryById {
        @Test
        @DisplayName("Given a valid grocery id when grocery is removed " +
                "then expect grocery item.")
        void given_ValidGroceryId_When_GroceryRemoved_Then_ExpectGroceryItem(){
            //given
            when(groceryItemRepository.existsById(Mockito.anyInt())).thenReturn(true);
            when(groceryItemRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(groceryItem));
            doNothing().when(groceryItemRepository).deleteById(Mockito.anyInt());

            //when
            GroceryItem retrievedGrocery = groceryItemService.removeGroceryById(Mockito.anyInt());

            //then
            assertEquals(groceryItem, retrievedGrocery);
        }

        @Test
        @DisplayName("Given a invalid grocery id when grocery is removed " +
                "then expect exception.")
        void given_InvalidGroceryId_When_GroceryRemoved_Then_ExpectException(){
            //when
            when(groceryItemRepository.existsById(Mockito.anyInt())).thenReturn(false);

            //then
            assertThrows(GroceryItemException.class,
                    () -> groceryItemService.removeGroceryById(Mockito.anyInt()));
        }
    }

    @Nested
    @DisplayName("Remove Quantity")
    class removeQuantity {
        @Test
        @DisplayName("Given a valid grocery id when one grocery quantity is decreased " +
                "then expect grocery item.")
        void given_ValidGroceryId_When_OneQuantityRemoved_Then_ExpectGroceryItem(){
            //given
            groceryItem.setQuantity(5);
            when(groceryItemRepository.existsById(Mockito.anyInt())).thenReturn(true);
            when(groceryItemRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(groceryItem));
            when(groceryItemRepository.save(groceryItem)).thenReturn(groceryItem);

            //when
            GroceryItem retrievedGrocery = groceryItemService.removeQuantity(Mockito.anyInt());

            //then
            assertEquals(groceryItem, retrievedGrocery);

            //making sure quantity has decreased to the correct amount
            assertEquals(4, retrievedGrocery.getQuantity());
        }

        @Test
        @DisplayName("Given a valid grocery id when one grocery quantity is decreased " +
                "then expect deleted grocery item.")
        void given_ValidGroceryId_When_OneQuantityRemoved_Then_ExpectDeletedGroceryItem(){
            //given
            groceryItem.setQuantity(1);
            when(groceryItemRepository.existsById(Mockito.anyInt())).thenReturn(true);
            when(groceryItemRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(groceryItem));
            doNothing().when(groceryItemRepository).deleteById(Mockito.anyInt());

            //when
            GroceryItem retrievedGrocery = groceryItemService.removeQuantity(Mockito.anyInt());

            //then
            assertEquals(groceryItem, retrievedGrocery);

            //making sure quantity is now 0
            assertEquals(0, retrievedGrocery.getQuantity());
        }

        @Test
        @DisplayName("Given invalid grocery item id when quantity is decreased " +
                "then expect exception.")
        void given_InvalidGroceryId_When_GroceryQuantityDecreased_Then_ExpectException(){
            //given
            when(groceryItemRepository.existsById(Mockito.anyInt())).thenReturn(false);

            //then
            assertThrows(GroceryItemException.class,
                    () -> groceryItemService.removeQuantity(Mockito.anyInt()));
        }

    }

    @Nested
    @DisplayName("Remove All Groceries")
    class deleteAll {
        @Test
        @DisplayName("Given no input data when all groceries are removed " +
                "then expect nothing.")
        void given_NoInputData_When_AllGroceriesRemoved_Then_ExpectNothing(){
            //given
            when(groceryItemRepository.count()).thenReturn(3l);
            doNothing().when(groceryItemRepository).deleteAll();

            //when
            groceryItemService.removeGroceries();

            //then
            //verify that these methods ran at least once
            verify(groceryItemRepository, times(1)).count();
            verify(groceryItemRepository, times(1)).deleteAll();
        }

        @Test
        @DisplayName("Given no input data when all groceries are removed " +
                "then expect exception.")
        void given_NoInputData_When_AllGroceriesRemoved_Then_ExpectException(){
            //when
            when(groceryItemRepository.count()).thenReturn(0l);

            //then
            assertThrows(GroceryItemException.class,
                    () -> groceryItemService.removeGroceries());
        }
    }
}