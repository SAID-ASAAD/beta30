package com.said.B30.infrastructure.repositories;

import com.said.B30.infrastructure.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
