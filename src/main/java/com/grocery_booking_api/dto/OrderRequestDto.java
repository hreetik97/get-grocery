package com.grocery_booking_api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.grocery_booking_api.utils.GroceryConstants.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    @NotNull(message = USER_ID_IS_MANDATORY)
    private Long userId;

    @NotEmpty(message = ORDER_MUST_CONTAIN_AT_LEAST_ONE_ITEM)
    private List<OrderItemRequestDto> items;
}
