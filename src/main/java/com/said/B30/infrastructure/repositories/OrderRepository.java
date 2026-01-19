package com.said.B30.infrastructure.repositories;

import com.said.B30.infrastructure.entities.Order;
import com.said.B30.infrastructure.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByClientId(Long clientId);

    List<Order> findByDescriptionContainingIgnoreCase(String description);

    @Query("SELECT o FROM Order o WHERE o.deliveryDate < :now AND o.paymentStatus = :status")
    List<Order> findOverdueOrders(@Param("now") LocalDate now, @Param("status") PaymentStatus status);

    @Query("SELECT o FROM Order o ORDER BY " +
           "CASE WHEN o.orderStatus IN (com.said.B30.infrastructure.enums.OrderStatus.COMPLETED, com.said.B30.infrastructure.enums.OrderStatus.CANCELED) THEN 1 ELSE 0 END, " +
           "o.deliveryDate ASC")
    Page<Order> findAllOrdersSortedByUrgency(Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.orderStatus != com.said.B30.infrastructure.enums.OrderStatus.CANCELED ORDER BY " +
           "CASE WHEN o.orderStatus = com.said.B30.infrastructure.enums.OrderStatus.COMPLETED THEN 1 ELSE 0 END, " +
           "o.deliveryDate ASC")
    Page<Order> findOrdersNotCanceled(Pageable pageable);

    @Query("SELECT COALESCE(SUM(o.establishedValue - (SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.order = o)), 0) " +
            "FROM Order o WHERE o.paymentStatus != 'PAYMENT_OK'")
    Double totalReceivable();

    @Query("SELECT COALESCE(SUM(o.establishedValue - (SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.order = o)), 0) " +
            "FROM Order o WHERE o.paymentStatus != 'PAYMENT_OK' AND o.deliveryDate BETWEEN :startDate AND :endDate")
    Double totalReceivableByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(o.establishedValue - (SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.order = o)), 0) " +
            "FROM Order o WHERE o.paymentStatus != 'PAYMENT_OK' AND o.client.id = :clientId")
    Double totalReceivableByClientId(@Param("clientId") Long clientId);

    @Query("SELECT COALESCE(o.establishedValue - (SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.order = o), 0) " +
            "FROM Order o WHERE o.id = :orderId")
    Double getReceivableAmount(@Param("orderId") Long orderId);

    @Query("SELECT COALESCE((SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.order = o) - (COALESCE(o.materialValue, 0) + COALESCE(o.externalServiceValue, 0)), 0) " +
            "FROM Order o WHERE o.id = :orderId")
    Double getProfitByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT COALESCE(SUM((SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.order = o) - (COALESCE(o.materialValue, 0) + COALESCE(o.externalServiceValue, 0))), 0) " +
            "FROM Order o")
    Double getTotalProfit();

    @Query("SELECT COALESCE(SUM((SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.order = o) - (COALESCE(o.materialValue, 0) + COALESCE(o.externalServiceValue, 0))), 0) " +
            "FROM Order o WHERE o.deliveryDate BETWEEN :startDate AND :endDate")
    Double getTotalProfitByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
