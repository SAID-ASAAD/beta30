package com.said.B30.infrastructure.repositories;

import com.said.B30.infrastructure.entities.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SellRepository extends JpaRepository<Sell, Long> {
    List<Sell> findByProductId(Long productId);
    List<Sell> findByClientId(Long clientId);

    @Query("SELECT COALESCE(SUM(s.totalValue - (SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.sell = s)), 0) " +
            "FROM Sell s WHERE s.paymentStatus != 'PAYMENT_OK'")
    Double totalReceivable();

    @Query("SELECT COALESCE(SUM(s.totalValue - (SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.sell = s)), 0) " +
            "FROM Sell s WHERE s.paymentStatus != 'PAYMENT_OK' AND s.saleDate BETWEEN :startDate AND :endDate")
    Double totalReceivableByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COALESCE(SUM(s.totalValue - (SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.sell = s)), 0) " +
            "FROM Sell s WHERE s.client.id = :clientId AND s.paymentStatus != 'PAYMENT_OK'")
    Double totalReceivableByClientId(@Param("clientId") Long clientId);

    @Query("SELECT COALESCE(s.totalValue - (SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.sell = s), 0) " +
            "FROM Sell s WHERE s.id = :sellId")
    Double getReceivableAmountBySellId(@Param("sellId") Long sellId);
    
    @Query("SELECT COALESCE(SUM(" +
           "(SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.sell = s) - " +
           "(s.quantity * ((s.product.materialValue + s.product.externalServiceValue) / " +
           "(s.product.quantity + (SELECT COALESCE(SUM(s2.quantity), 0) FROM Sell s2 WHERE s2.product = s.product))))" +
           "), 0) " +
           "FROM Sell s WHERE s.product.id = :productId")
    Double getProfitByProductId(@Param("productId") Long productId);

    @Query("SELECT COALESCE(SUM(" +
           "(SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.sell = s) - " +
           "(s.quantity * ((s.product.materialValue + s.product.externalServiceValue) / " +
           "(s.product.quantity + (SELECT COALESCE(SUM(s2.quantity), 0) FROM Sell s2 WHERE s2.product = s.product))))" +
           "), 0) " +
           "FROM Sell s")
    Double getTotalProfit();

    @Query("SELECT COALESCE(SUM(" +
           "(SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.sell = s) - " +
           "(s.quantity * ((s.product.materialValue + s.product.externalServiceValue) / " +
           "(s.product.quantity + (SELECT COALESCE(SUM(s2.quantity), 0) FROM Sell s2 WHERE s2.product = s.product))))" +
           "), 0) " +
           "FROM Sell s WHERE s.saleDate BETWEEN :startDate AND :endDate")
    Double getTotalProfitByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
