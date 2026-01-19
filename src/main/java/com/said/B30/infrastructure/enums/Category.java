package com.said.B30.infrastructure.enums;

import lombok.Getter;

@Getter
public enum Category {

    MANUFACTURING("FABRICAÇÃO"),
    MAINTENANCE("MANUTENÇÃO");

    private final String description;


    Category(String description){
        this.description = description;
    }


}
