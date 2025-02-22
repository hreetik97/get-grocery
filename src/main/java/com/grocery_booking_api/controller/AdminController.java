package com.grocery_booking_api.controller;

import com.grocery_booking_api.dto.GroceryItemDto;
import com.grocery_booking_api.service.GroceryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static com.grocery_booking_api.utils.GroceryConstants.*;

@RestController
@RequestMapping("api/admin")
@RequiredArgsConstructor
@CrossOrigin
public class AdminController {

    private final GroceryService groceryService;

    @PostMapping("/grocery-items")
    public ResponseEntity<GroceryItemDto> addGroceryItem(@Valid @RequestBody GroceryItemDto request) {
        GroceryItemDto newItem = groceryService.addGroceryItem(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
    }

    @GetMapping("/grocery-items")
    public ResponseEntity<List<GroceryItemDto>> getAllGroceryItems() {
        return ResponseEntity.ok(groceryService.getAllGroceryItems());
    }

    @PutMapping("/grocery-items/{id}")
    public ResponseEntity<GroceryItemDto> updateGroceryItem(@PathVariable Long id, @Valid @RequestBody GroceryItemDto request) {
        return ResponseEntity.ok(groceryService.updateGroceryItem(id, request));
    }

    @DeleteMapping("/grocery-items/{id}")
    public ResponseEntity<String> deleteGroceryItem(@PathVariable Long id) {
        groceryService.deleteGroceryItem(id);
        return ResponseEntity.ok(GROCERY_ITEM_DELETED_SUCCESSFULLY);
    }

}
