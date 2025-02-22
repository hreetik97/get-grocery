package com.grocery_booking_api.service;

import com.grocery_booking_api.dto.GroceryItemDto;

import java.util.List;

public interface GroceryService {
    GroceryItemDto addGroceryItem( GroceryItemDto request);

    List<GroceryItemDto> getAllGroceryItems();

    GroceryItemDto updateGroceryItem(Long id, GroceryItemDto request);

    void deleteGroceryItem(Long id);
    List<GroceryItemDto> getAllAvailableGroceryItems();
}
