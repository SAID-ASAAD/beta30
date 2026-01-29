package com.said.B30.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.said.B30.infrastructure.enums.Category;
import com.said.B30.infrastructure.enums.OrderStatus;
import com.said.B30.infrastructure.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private Category category;

    @Column(name = "descrição", nullable = false)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "data_do_pedido")
    private LocalDate orderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "data_de_entrega", nullable = false)
    private LocalDate deliveryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "data_de_saída")
    private LocalDate exitDate;

    @Column(name = "valor_cobrado", nullable = false)
    private Double establishedValue;

    @Column(name = "valor_serviço_terceiro")
    private Double externalServiceValue;

    @Column(name = "valor_de_material")
    private Double materialValue;

    @Column(name = "sinal", nullable = false)
    private Double deposit;

    @Column(name = "nota_fiscal")
    private String invoice;

    @Column(name = "observações_da_produção")
    private String productionProcessNote;

    @Column(name = "problema_inesperado")
    private String unexpectedIssue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_do_pedido")
    private OrderStatus orderStatus;

    @Column(name = "status_do_pagamento")
    private PaymentStatus paymentStatus;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private Set<Payment> payments = new HashSet<>();

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setOrder(this);
    }

    @PrePersist
    private void prePersist(){
        this.orderDate = LocalDate.now();
        this.orderStatus = OrderStatus.IN_PROGRESS;
        if(this.deposit >= this.establishedValue){
            this.paymentStatus = PaymentStatus.PAYMENT_OK;
        } else if (this.deposit > 0 && this.deposit < this.establishedValue) {
            this.paymentStatus = PaymentStatus.DEPOSIT_PAID;
        } else {
            this.paymentStatus = PaymentStatus.PENDING_DEPOSIT;
        }
    }
}
