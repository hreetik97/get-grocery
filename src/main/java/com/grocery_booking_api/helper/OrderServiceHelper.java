package com.grocery_booking_api.helper;

import com.grocery_booking_api.entities.Order;
import com.grocery_booking_api.entities.OrderItem;
import com.grocery_booking_api.dto.OrderItemResponseDto;
import com.grocery_booking_api.dto.OrderResponseDto;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceHelper {

    public OrderResponseDto getOrderResponseDto(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .userId(order.getUser().getId())
                .userName(order.getUser().getUsername())
                .items(order.getOrderItems().stream()
                        .map(this::getOrderItemResponseDto)
                        .toList())
                .totalCost(order.getOrderItems().stream()
                        .mapToDouble(item -> item.getQuantity() * item.getGroceryItem().getPrice())
                        .sum())
                .build();
    }

    public OrderItemResponseDto getOrderItemResponseDto(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .itemId(orderItem.getGroceryItem().getId())
                .itemName(orderItem.getGroceryItem().getName())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getGroceryItem().getPrice())
                .totalPrice(orderItem.getQuantity() * orderItem.getGroceryItem().getPrice())
                .build();
    }
}
