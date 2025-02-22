package com.grocery_booking_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class GroceryServiceException extends RuntimeException {
    public GroceryServiceException(String message) {
        super(message);
    }
}