package com.grocery_booking_api.repositories;

import com.grocery_booking_api.entities.GroceryItem;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GroceryRepository extends JpaRepository<GroceryItem, Long> {

    @Query("SELECT i FROM GroceryItem i WHERE i.inventoryCount > 0")
    List<GroceryItem> findAvailableGroceryItems();

    Optional<GroceryItem> findByName( String name);
}
