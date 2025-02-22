package com.grocery_booking_api.repositories;

import com.grocery_booking_api.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
