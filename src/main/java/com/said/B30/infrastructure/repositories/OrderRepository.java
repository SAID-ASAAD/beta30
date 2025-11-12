package com.said.B30.infrastructure.repositories;

import com.said.B30.infrastructure.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
