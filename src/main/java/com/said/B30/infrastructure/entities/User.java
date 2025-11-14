package com.said.B30.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "usuários")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "nome", nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(name = "senha", nullable = false)
    private String password;
    @Column(name = "ativo", nullable = false)
    private boolean active;

    @PrePersist
    private void prePersist(){
        this.active = true;
    }


}
