package com.caseyjofleck.simplegrocerylist.controller.ControllerAdvice;

import com.caseyjofleck.simplegrocerylist.exception.GroceryItemException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GroceryControllerAdvice {
    private final Logger LOGGER = LoggerFactory.getLogger(GroceryControllerAdvice.class);
    /**
     * HandleGroceryItemException - GroceryItemException.
     *
     * @param ex      - exception
     * @return - response
     */
    @ExceptionHandler(GroceryItemException.class)
    public ResponseEntity<?> handleGroceryItemException(GroceryItemException ex) {
        LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity<>(this.createBody(ex), ex.getStatusCode());
    }

    /**
     * HandleException - Exception.
     *
     * @param ex      - exception
     * @return - response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGroceryItemException(Exception ex) {
        LOGGER.error("An internal error has occurred. Please view stack trace.");
        LOGGER.error(String.valueOf(ex));

        return new ResponseEntity<>(
                this.createBody(new GroceryItemException("An internal error has occurred. Please try again later.")),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, Object> createBody(GroceryItemException ex){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return body;
    }
}
