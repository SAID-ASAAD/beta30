package com.said.B30.infrastructure.repositories;

import com.said.B30.infrastructure.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByDescriptionContainingIgnoreCase(String description);

}
