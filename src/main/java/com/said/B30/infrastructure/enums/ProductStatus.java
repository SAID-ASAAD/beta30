package com.said.B30.infrastructure.enums;

import lombok.Getter;

@Getter
public enum ProductStatus {

    AVAILABLE("DISPONÍVEL"),
    SOLD_OUT("ESGOTADO");

    private final String description;

    ProductStatus(String description){
        this.description = description;
    }
}
