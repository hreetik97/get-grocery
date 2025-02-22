package com.grocery_booking_api.service;

import com.grocery_booking_api.dto.OrderRequestDto;
import com.grocery_booking_api.dto.OrderResponseDto;
import jakarta.validation.Valid;

public interface OrderService {

    OrderResponseDto placeOrder(@Valid OrderRequestDto orderRequestDto);
}
