package com.grocery_booking_api.service;

import com.grocery_booking_api.entities.GroceryItem;
import com.grocery_booking_api.exception.GroceryItemAlreadyExistsException;
import com.grocery_booking_api.exception.GroceryServiceException;
import com.grocery_booking_api.helper.GroceryServiceHelper;
import com.grocery_booking_api.dto.GroceryItemDto;
import com.grocery_booking_api.repositories.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.grocery_booking_api.utils.GroceryConstants.*;

@Service
public class GroceryServiceImpl implements GroceryService {

    private final GroceryRepository groceryRepository;
    private final GroceryServiceHelper groceryServiceHelper;

    @Autowired
    public GroceryServiceImpl(GroceryRepository groceryRepository, GroceryServiceHelper groceryServiceHelper) {
        this.groceryRepository = groceryRepository;
        this.groceryServiceHelper = groceryServiceHelper;
    }

    @Override
    @Transactional
    public GroceryItemDto addGroceryItem(GroceryItemDto groceryItem) {

        GroceryItem item = groceryServiceHelper.getGroceryItem(groceryItem);
        if(groceryRepository.findByName(item.getName()).isPresent())
            throw new GroceryItemAlreadyExistsException(GROCERY_ITEM_WITH_NAME + item.getName() + ALREADY_EXISTS, HttpStatus.CONFLICT);
        else
           return groceryServiceHelper.getGroceryItemDto(groceryRepository.save(groceryServiceHelper.getGroceryItem(groceryItem)));
    }

    public List<GroceryItemDto> getAllGroceryItems() {
        Optional<List<GroceryItem>> groceryItems = Optional.of(groceryRepository.findAll());
        return groceryItems.map(groceryServiceHelper::getGroceryItemList).orElse(Collections.emptyList());
    }

    public List<GroceryItemDto> getAllAvailableGroceryItems() {
        Optional<List<GroceryItem>> groceryItems = Optional.ofNullable(groceryRepository.findAvailableGroceryItems());
        return groceryItems.map(groceryServiceHelper::getGroceryItemList).orElse(Collections.emptyList());
    }

    public GroceryItemDto updateGroceryItem(Long id, GroceryItemDto groceryItem) {
        GroceryItem item = groceryRepository.findByName(groceryItem.getName())
                .orElseThrow(() -> new GroceryServiceException(GROCERY_ITEM_NOT_FOUND));
        item.setName(groceryItem.getName());
        item.setPrice(groceryItem.getPrice());
        item.setInventoryCount(groceryItem.getInventoryCount());
        return groceryServiceHelper.getGroceryItemDto(groceryRepository.save(item));
    }

    public void deleteGroceryItem(Long id) {
        GroceryItem item = groceryRepository.findById(id)
                .orElseThrow(() -> new GroceryServiceException(GROCERY_ITEM_NOT_FOUND));
        groceryRepository.delete(item);
    }

}
