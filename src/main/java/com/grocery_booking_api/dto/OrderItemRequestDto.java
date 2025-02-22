package com.grocery_booking_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.grocery_booking_api.utils.GroceryConstants.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequestDto {

    @NotNull(message = ORDER_NAME_ID_IS_MANDATORY)
    private String name;

    @NotNull(message = QUANTITY_IS_MANDATORY)
    @Min(value = 1, message = QUANTITY_MUST_BE_AT_LEAST_1)
    private Integer quantity;
}
