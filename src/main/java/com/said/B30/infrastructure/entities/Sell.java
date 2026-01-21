package com.said.B30.infrastructure.entities;

import com.said.B30.infrastructure.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "vendas")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Sell implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "quantidade", nullable = false)
    private Integer quantity;

    @Column(name = "valor_unitario", nullable = false)
    private Double unitValue;

    @Column(name = "valor_total", nullable = false)
    private Double totalValue;

    @Column(name = "data_venda", nullable = false)
    private LocalDate saleDate;

    @Column(name = "status_pagamento")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "sell", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Payment> payments = new HashSet<>();

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setSell(this);
    }

    @PrePersist
    private void prePersist() {
        this.saleDate = LocalDate.now();
        if (this.totalValue == null && this.quantity != null && this.unitValue != null) {
            this.totalValue = this.quantity * this.unitValue;
        }
    }
}
