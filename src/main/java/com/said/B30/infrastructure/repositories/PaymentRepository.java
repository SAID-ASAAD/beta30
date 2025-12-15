package com.said.B30.infrastructure.repositories;

import com.said.B30.infrastructure.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
