package com.grocery_booking_api.helper;

import com.grocery_booking_api.entities.GroceryItem;
import com.grocery_booking_api.dto.GroceryItemDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroceryServiceHelper {

    public GroceryItem getGroceryItem(GroceryItemDto groceryItem) {
        return GroceryItem.builder()
                .name(groceryItem.getName())
                .price(groceryItem.getPrice())
                .inventoryCount(groceryItem.getInventoryCount())
                .build();
    }

    public List<GroceryItemDto> getGroceryItemList(List<GroceryItem> availableGroceryItems) {
        return availableGroceryItems.stream()
                .map(groceryItem -> GroceryItemDto.builder()
                        .id(groceryItem.getId())
                        .name(groceryItem.getName())
                        .price(groceryItem.getPrice())
                        .inventoryCount(groceryItem.getInventoryCount())
                        .build())
                .toList();
    }

    public GroceryItemDto getGroceryItemDto(GroceryItem item) {
        return GroceryItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .inventoryCount(item.getInventoryCount())
                .build();
    }

}
