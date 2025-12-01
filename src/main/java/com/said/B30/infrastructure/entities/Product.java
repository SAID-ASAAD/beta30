package com.said.B30.infrastructure.entities;

import com.said.B30.infrastructure.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product {

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
    @Column(name = "valor_cobrado", nullable = false)
    private Double establishedValue;
    @Column(name = "status_do_produto", nullable = false)
    private ProductStatus productStatus;
    @Column(name = "nota_fiscal")
    private String invoice;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @PrePersist
    private void prePersist(){
        this.productionDate = LocalDate.now();
        this.productStatus = ProductStatus.AVAILABLE;
    }
}
