package com.said.B30.infrastructure.repositories;

import com.said.B30.infrastructure.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT COALESCE(SUM(prod.establishedValue - (SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.product = prod)), 0) " +
            "FROM Product prod WHERE prod.paymentStatus != 'PAYMENT_OK'")
    Double totalReceivable();

    @Query("SELECT COALESCE(SUM(prod.establishedValue - (SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.product = prod)), 0) " +
            "FROM Product prod WHERE prod.paymentStatus != 'PAYMENT_OK' AND prod.client.id = :clientId")
    Double totalReceivableByClientId(@Param("clientId") Long clientId);

    @Query("SELECT COALESCE(prod.establishedValue - (SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.product = prod), 0) " +
            "FROM Product prod WHERE prod.id = :productId")
    Double getReceivableAmount(@Param("productId") Long productId);

    @Query("SELECT COALESCE((SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.product = prod) - (COALESCE(prod.materialValue, 0) + COALESCE(prod.externalServiceValue, 0)), 0) " +
            "FROM Product prod WHERE prod.id = :productId")
    Double getProfitByProductId(@Param("productId") Long productId);

    @Query("SELECT COALESCE(SUM((SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.product = prod) - (COALESCE(prod.materialValue, 0) + COALESCE(prod.externalServiceValue, 0))), 0) " +
            "FROM Product prod")
    Double getTotalProfit();

    @Query("SELECT COALESCE(SUM((SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.product = prod) - (COALESCE(prod.materialValue, 0) + COALESCE(prod.externalServiceValue, 0))), 0) " +
            "FROM Product prod WHERE prod.saleDate BETWEEN :startDate AND :endDate")
    Double getTotalProfitByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
