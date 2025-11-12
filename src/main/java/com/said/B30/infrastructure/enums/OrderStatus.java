package com.said.B30.infrastructure.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {

    IN_PROGRESS("em_andamento"),
    CANCELED("cancelado"),
    COMPLETED("concluído");

    private final String description;

    OrderStatus(String description){
        this.description = description;
    }

}
