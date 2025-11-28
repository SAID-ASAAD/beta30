package com.said.B30.infrastructure.enums;

import lombok.Getter;

@Getter
public enum ProductStatus {

    AVAILABLE("disponível"),
    SOLD("vendido");

    private final String description;

    ProductStatus(String description){
        this.description = description;
    }
}
