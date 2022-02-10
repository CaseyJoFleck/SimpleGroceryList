package com.caseyjofleck.simplegrocerylist.controller;

import com.caseyjofleck.simplegrocerylist.controller.ControllerAdvice.GroceryControllerAdvice;
import com.caseyjofleck.simplegrocerylist.exception.GroceryItemException;
import com.caseyjofleck.simplegrocerylist.model.GroceryItem;
import com.caseyjofleck.simplegrocerylist.service.GroceryItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

class GroceryItemControllerTest {

    private GroceryItemService groceryItemService;

    private GroceryItemController groceryItemController;

    private GroceryItem groceryItem;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        groceryItemService = Mockito.mock(GroceryItemService.class);
        groceryItemController = new GroceryItemController(groceryItemService);
        mockMvc = MockMvcBuilders.standaloneSetup(groceryItemController)
                .setControllerAdvice(GroceryControllerAdvice.class).build();

        groceryItem = new GroceryItem();
        groceryItem.setQuantity(1);
        groceryItem.setName("apple");
        groceryItem.setId(1);
    }

    @Nested
    @DisplayName("Get All Groceries")
    class getGroceries {
        @Test
        @DisplayName("Given no input data when groceries are found " +
                "then expect OK HTTP Status Code.")
        void given_NoInputData_When_GroceriesFound_Then_ExpectOk() throws Exception {
            //when
            Mockito.when(groceryItemService.getGroceries()).thenReturn(List.of(new GroceryItem()));

            //then
            mockMvc.perform(MockMvcRequestBuilders.get("/groceries")).andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("Given no input data when groceries are not found " +
                "then expect Not Found HTTP Status Code.")
        void given_NoInputData_When_GroceriesNotFound_Then_ExpectNotFound() throws Exception {
            //when
            doThrow(new GroceryItemException("No groceries found.", HttpStatus.NOT_FOUND))
                    .when(groceryItemService).getGroceries();

            //then
            mockMvc.perform(MockMvcRequestBuilders.get("/groceries")).andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        @DisplayName("Given no input data when server side error occurs " +
                "then expect Internal Server Error HTTP Status Code.")
        void given_NoInputData_When_ServerSideErrorOccurs_Then_ExpectInternalServerError() throws Exception {
            //when
            doThrow(new RuntimeException())
                    .when(groceryItemService).getGroceries();

            //then
            mockMvc.perform(MockMvcRequestBuilders.get("/groceries")).andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }
    }

    @Nested
    @DisplayName("Add Grocery")
    class addGrocery {
        @Test
        @DisplayName("Given a grocery name when grocery is added " +
                "then expect OK HTTP Status Code.")
        void given_ValidGroceryName_When_GroceryAdded_Then_ExpectOk() throws Exception {
            //when
            Mockito.when(groceryItemService.addGrocery(new GroceryItem())).thenReturn(groceryItem);

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/groceries")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"apple\"}"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("Given an invalid quantity when grocery is added " +
                "then expect Bad Request HTTP Status Code.")
        void given_InvalidQuantity_When_GroceryAdded_Then_ExpectBadRequest() throws Exception {
            //when
            doThrow(new GroceryItemException("The quantity of items must be greater than 0.", HttpStatus.BAD_REQUEST))
                    .when(groceryItemService).addGrocery(Mockito.any());

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/groceries")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\":\"apple\", \"quantity\": -1}"))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        @DisplayName("Given missing name field when grocery is added " +
                "then expect Bad Request HTTP Status Code.")
        void given_MissingName_When_GroceryAdded_Then_ExpectBadRequest() throws Exception {
            //when
            doThrow(new GroceryItemException("Grocery item name must not be blank when adding new item.", HttpStatus.BAD_REQUEST))
                    .when(groceryItemService).addGrocery(Mockito.any());

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .post("/groceries")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"name\": \"Banana\"}"))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());
        }

        @Test
        @DisplayName("Given a grocery item when internal server error occurs " +
                "then expect Internal Server Error HTTP Status Code.")
        void given_ValidGroceryItem_When_InternalServerErrorOccurs_Then_ExpectInternalServerError() throws Exception {
            //when
            doThrow(new RuntimeException())
                    .when(groceryItemService).addGrocery(Mockito.any());

            //then
            mockMvc.perform(MockMvcRequestBuilders.post("/groceries")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"name\":\"apple\"}")
            ).andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }
    }

    @Nested
    @DisplayName("Add Quantity")
    class addQuantity {
        @Test
        @DisplayName("Given a valid grocery id when quantity is increased " +
                "then expect OK HTTP Status Code.")
        void given_ValidGroceryId_When_QuantityIncreased_Then_ExpectOk() throws Exception {
            //when
            Mockito.when(groceryItemService.addQuantity(Mockito.anyInt())).thenReturn(groceryItem);

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .put("/groceries/{id}", "1"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("Given an invalid grocery id when quantity is increased " +
                "then expect Not Found HTTP Status Code.")
        void given_InvalidId_When_QuantityIncreased_Then_ExpectNotFound() throws Exception {
            //when
            doThrow(new GroceryItemException("Operation cannot continue. Item not found in grocery list.", HttpStatus.NOT_FOUND))
                    .when(groceryItemService).addQuantity(Mockito.anyInt());

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .put("/groceries/{id}", "1"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        @DisplayName("Given a grocery item when internal server error occurs " +
                "then expect Internal Server Error HTTP Status Code.")
        void given_ValidGroceryItem_When_InternalServerErrorOccurs_Then_ExpectInternalServerError() throws Exception {
            //when
            doThrow(new RuntimeException())
                    .when(groceryItemService).addQuantity(Mockito.anyInt());

            //then
            mockMvc.perform(MockMvcRequestBuilders
                    .put("/groceries/{id}", "1"))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }

    }

    @Nested
    @DisplayName("Remove Quantity")
    class removeQuantity {
        @Test
        @DisplayName("Given a valid grocery id when quantity is decreased " +
                "then expect OK HTTP Status Code.")
        void given_ValidGroceryId_When_QuantityDecreased_Then_ExpectOk() throws Exception {
            //when
            Mockito.when(groceryItemService.removeQuantity(Mockito.anyInt())).thenReturn(groceryItem);

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/groceries/{id}", "1"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("Given an invalid grocery id when quantity is decreased " +
                "then expect Not Found HTTP Status Code.")
        void given_InvalidId_When_QuantityDecreased_Then_ExpectNotFound() throws Exception {
            //when
            doThrow(new GroceryItemException("Operation cannot continue. Item not found in grocery list.", HttpStatus.NOT_FOUND))
                    .when(groceryItemService).removeQuantity(Mockito.anyInt());

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/groceries/{id}", "1"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        @DisplayName("Given a grocery item when internal server error occurs " +
                "then expect Internal Server Error HTTP Status Code.")
        void given_ValidGroceryItem_When_InternalServerErrorOccurs_Then_ExpectInternalServerError() throws Exception {
            //when
            doThrow(new RuntimeException())
                    .when(groceryItemService).removeQuantity(Mockito.anyInt());

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/groceries/{id}", "1"))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }
    }

    @Nested
    @DisplayName("Remove Grocery")
    class removeGrocery {
        @Test
        @DisplayName("Given a valid grocery id when grocery is removed " +
                "then expect OK HTTP Status Code.")
        void given_ValidGroceryId_When_GroceryRemoved_Then_ExpectOk() throws Exception {
            //when
            Mockito.when(groceryItemService.removeGroceryById(Mockito.anyInt())).thenReturn(groceryItem);

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/groceries/all/{id}", "1"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("Given an invalid grocery id when grocery is removed " +
                "then expect Not Found HTTP Status Code.")
        void given_InvalidId_When_GroceryRemoved_Then_ExpectNotFound() throws Exception {
            //when
            doThrow(new GroceryItemException("Operation cannot continue. Item not found in grocery list.", HttpStatus.NOT_FOUND))
                    .when(groceryItemService).removeGroceryById(Mockito.anyInt());

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/groceries/all/{id}", "1"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        @DisplayName("Given a grocery item when internal server error occurs " +
                "then expect Internal Server Error HTTP Status Code.")
        void given_ValidGroceryItem_When_InternalServerErrorOccurs_Then_ExpectInternalServerError() throws Exception {
            //when
            doThrow(new RuntimeException())
                    .when(groceryItemService).removeGroceryById(Mockito.anyInt());

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/groceries/all/{id}", "1"))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }
    }

    @Nested
    @DisplayName("Remove All Groceries")
    class deleteAll {
        @Test
        @DisplayName("Given no input data when all groceries are removed " +
                "then expect OK HTTP Status Code.")
        void given_NoInputData_When_AllGroceriesRemoved_Then_ExpectOk() throws Exception {
            //when
            doNothing().when(groceryItemService).removeGroceries();

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/groceries"))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        }

        @Test
        @DisplayName("Given an invalid grocery id when grocery is removed " +
                "then expect Not Found HTTP Status Code.")
        void given_InvalidId_When_GroceryRemoved_Then_ExpectNotFound() throws Exception {
            //when
            doThrow(new GroceryItemException("No groceries found.", HttpStatus.NOT_FOUND))
                    .when(groceryItemService).removeGroceries();

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/groceries"))
                    .andExpect(MockMvcResultMatchers.status().isNotFound());
        }

        @Test
        @DisplayName("Given a grocery item when internal server error occurs " +
                "then expect Internal Server Error HTTP Status Code.")
        void given_ValidGroceryItem_When_InternalServerErrorOccurs_Then_ExpectInternalServerError() throws Exception {
            //when
            doThrow(new RuntimeException())
                    .when(groceryItemService).removeGroceries();

            //then
            mockMvc.perform(MockMvcRequestBuilders
                            .delete("/groceries"))
                    .andExpect(MockMvcResultMatchers.status().isInternalServerError());
        }
    }

}