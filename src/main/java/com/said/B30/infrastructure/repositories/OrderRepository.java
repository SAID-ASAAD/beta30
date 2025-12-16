package com.said.B30.infrastructure.repositories;

import com.said.B30.infrastructure.entities.Order;
import com.said.B30.infrastructure.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o WHERE o.deliveryDate < :now AND o.paymentStatus = :status")
    List<Order> findOverdueOrders(@Param("now") LocalDateTime now, @Param("status") PaymentStatus status);
}
