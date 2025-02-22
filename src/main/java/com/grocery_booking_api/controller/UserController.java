package com.grocery_booking_api.controller;

import com.grocery_booking_api.dto.*;
import com.grocery_booking_api.service.GroceryService;
import com.grocery_booking_api.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grocery")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final GroceryService groceryService;
    private final OrderService orderService;

    @GetMapping("/grocery-items")
    public ResponseEntity<List<GroceryItemDto>> getAvailableGroceryItems() {
        return ResponseEntity.ok(groceryService.getAllAvailableGroceryItems());
    }

    @PostMapping("/order-grocery")
    public ResponseEntity<OrderResponseDto> placeOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto orderResponse = orderService.placeOrder(orderRequestDto);
        return ResponseEntity.ok(orderResponse);
    }

}
