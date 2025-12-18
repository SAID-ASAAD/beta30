package com.said.B30.infrastructure.entities;

import com.said.B30.infrastructure.enums.PaymentStatus;
import com.said.B30.infrastructure.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "descrição", nullable = false)
    private String description;
    @Column(name = "observações_da_produção")
    private String productionProcessNote;
    @Column(name = "data_de_produção")
    private LocalDate productionDate;
    @Column(name = "data_de_venda")
    private LocalDate saleDate;
    @Column(name = "valor_de_material", nullable = false)
    private Double materialValue;
    @Column(name = "valor_serviço_terceiro", nullable = false)
    private Double externalServiceValue;
    @Column(name = "valor_sugerido", nullable = false)
    private Double preEstablishedValue;
    @Column(name = "valor_cobrado")
    private Double establishedValue;
    @Column(name = "status_do_produto", nullable = false)
    private ProductStatus productStatus;
    @Column(name = "status_do_pagamento")
    private PaymentStatus paymentStatus;
    @Column(name = "nota_fiscal")
    private String invoice;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Payment> payments = new HashSet<>();

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setProduct(this);
    }

    @PrePersist
    private void prePersist(){
        this.productionDate = LocalDate.now();
        this.productStatus = ProductStatus.AVAILABLE;
    }
}
