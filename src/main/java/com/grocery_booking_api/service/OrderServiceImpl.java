package com.grocery_booking_api.service;

import com.grocery_booking_api.entities.*;
import com.grocery_booking_api.exception.GroceryServiceException;
import com.grocery_booking_api.helper.OrderServiceHelper;
import com.grocery_booking_api.dto.OrderRequestDto;
import com.grocery_booking_api.dto.OrderResponseDto;
import com.grocery_booking_api.repositories.GroceryRepository;
import com.grocery_booking_api.repositories.OrderRepository;
import com.grocery_booking_api.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.grocery_booking_api.utils.GroceryConstants.*;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final GroceryRepository groceryRepository;
    private final UserRepository userRepository;
    private final OrderServiceHelper orderServiceHelper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, GroceryRepository groceryRepository, UserRepository userRepository, OrderServiceHelper orderServiceHelper) {
        this.userRepository = userRepository;
        this.groceryRepository = groceryRepository;
        this.orderRepository = orderRepository;
        this.orderServiceHelper = orderServiceHelper;
    }

    @Override
    @Transactional
    public OrderResponseDto placeOrder(OrderRequestDto orderRequestDto) {
        User user = userRepository.findById(orderRequestDto.getUserId())
                .orElseThrow(() -> new GroceryServiceException(USER_NOT_FOUND_WITH_ID + orderRequestDto.getUserId()));

        Order order = Order.builder()
                .user(user)
                .build();

        List<OrderItem> orderItems = orderRequestDto.getItems().stream().map(item -> {
            GroceryItem groceryItem = groceryRepository.findByName(item.getName())
                    .orElseThrow(() -> new GroceryServiceException(GROCERY_ITEM_NOT_FOUND_WITH_NAME + item.getName()));

            if (groceryItem.getInventoryCount() < item.getQuantity()) {
                throw new IllegalArgumentException(INSUFFICIENT_STOCK_FOR_ITEM + groceryItem.getName());
            }

            groceryItem.setInventoryCount(groceryItem.getInventoryCount() - item.getQuantity());
            groceryRepository.save(groceryItem);

            return OrderItem.builder()
                    .order(order)
                    .groceryItem(groceryItem)
                    .quantity(item.getQuantity())
                    .build();
        }).toList();
        order.setOrderItems(orderItems);
        order.setStatus(OrderStatus.PENDING);

        return orderServiceHelper.getOrderResponseDto(orderRepository.save(order));
    }

}