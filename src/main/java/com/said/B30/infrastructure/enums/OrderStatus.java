package com.said.B30.infrastructure.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    IN_PROGRESS("EM_ANDAMENTO"),
    CANCELED("CANCELADO"),
    COMPLETED("CONCLUÍDO");

    private final String description;

    OrderStatus(String description){
        this.description = description;
    }

}
