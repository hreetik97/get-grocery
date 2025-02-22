package com.grocery_booking_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import static com.grocery_booking_api.utils.GroceryConstants.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroceryItemDto {

    private Long id;

    @NotBlank(message = NAME_IS_MANDATORY)
    private String name;

    @NotNull(message = PRICE_IS_MANDATORY)
    @Positive(message = PRICE_MUST_BE_GREATER_THAN_0)
    private Double price;

    @NotNull(message = INVENTORY_COUNT_IS_MANDATORY)
    @Min(value = 0, message = INVENTORY_COUNT_CANNOT_BE_NEGATIVE)
    private Integer inventoryCount;

}

