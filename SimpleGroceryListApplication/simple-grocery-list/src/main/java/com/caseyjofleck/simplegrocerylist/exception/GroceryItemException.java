package com.caseyjofleck.simplegrocerylist.exception;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

public class GroceryItemException extends RuntimeException{
    private final HttpStatus statusCode;
    private final String message;

    public GroceryItemException(String message) {
        super(message);

        this.message = message;
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public GroceryItemException(String message, Throwable throwable) {
        super(message, throwable);

        this.message = message;
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public GroceryItemException(String message, HttpStatus statusCode){
        super(message);
        this.message = message;
        this.statusCode = statusCode;
    }

    public GroceryItemException(String message, HttpStatus statusCode, Throwable throwable){
        super(message, throwable);
        this.message = message;
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatusCode(){
        return statusCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("statusCode", statusCode.value())
                .append("message", message)
                .append("error", statusCode.getReasonPhrase())
                .toString();
    }
}
