package com.grocery_booking_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class GroceryBookingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GroceryBookingAppApplication.class, args);
	}

}
