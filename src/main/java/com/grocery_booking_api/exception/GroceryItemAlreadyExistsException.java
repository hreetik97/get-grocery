package com.grocery_booking_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GroceryItemAlreadyExistsException extends RuntimeException {

    private final HttpStatus conflict;

    public GroceryItemAlreadyExistsException(String message, HttpStatus conflict) {
        super(message);
        this.conflict = conflict;
    }

}
