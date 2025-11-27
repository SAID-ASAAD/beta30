package com.said.B30.infrastructure.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clientes")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Client implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String name;
    @Column(name = "telefone", nullable = false, unique = true)
    private String telephoneNumber;
    private String email;
    @Column(name = "observações")
    private String note;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    @Builder.Default
    private Set<Order> orders = new HashSet<>();
}
