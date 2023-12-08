package com.example.ecommerce.repositories;

import com.example.ecommerce.entities.Order;
import com.example.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
