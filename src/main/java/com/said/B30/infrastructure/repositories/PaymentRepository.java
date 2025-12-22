package com.said.B30.infrastructure.repositories;

import com.said.B30.infrastructure.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p")
    Double totalRevenue();

    List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    Double totalRevenueByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT p FROM Payment p " +
            "LEFT JOIN p.order o " +
            "LEFT JOIN p.product prod " +
            "WHERE o.client.id = :clientId OR prod.client.id = :clientId")
    List<Payment> findPaymentsByClientId(@Param("clientId") Long clientId);

    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p " +
            "LEFT JOIN p.order o " +
            "LEFT JOIN p.product prod " +
            "WHERE o.client.id = :clientId OR prod.client.id = :clientId")
    Double totalRevenueByClientId(@Param("clientId") Long clientId);

}
