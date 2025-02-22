package com.grocery_booking_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDto {

    private Long itemId;
    private String itemName;
    private Integer quantity;
    private Double price;
    private Double totalPrice;
}
