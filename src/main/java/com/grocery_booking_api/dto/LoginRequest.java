package com.grocery_booking_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.grocery_booking_api.utils.GroceryConstants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = USERNAME_IS_REQUIRED)
    private String username;
    @NotBlank(message = PASSWORD_IS_REQUIRED)
    private String password;
}

