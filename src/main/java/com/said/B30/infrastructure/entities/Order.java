package com.said.B30.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.said.B30.infrastructure.enums.Category;
import com.said.B30.infrastructure.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Pedidos")
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
    @Column(name = "descrição", nullable = false)
    private String description;
    @Column(name = "categoria", nullable = false)
    private Category category;
    @Column(name = "data_do_pedido")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime orderDate;
    @Column(name = "data_de_entrega", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime deliveryDate;
    @Column(name = "valor_de_material")
    private Double materialValue;
    @Column(name = "valor_cobrado", nullable = false)
    private Double establishedValue;
    @Column(name = "valor_serviço_terceiro", nullable = false)
    private Double externalServiceValue;
    @Column(name = "status_do_pedido")
    private OrderStatus orderStatus;
    @Column(name = "observações_da_produção")
    private String productionProcessNote;
    @Column(name = "nota_fiscal")
    private String invoice;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;


    @PrePersist
    private void prePersist(){
        this.orderDate = LocalDateTime.now();
        this.orderStatus = OrderStatus.IN_PROGRESS;
    }

}
