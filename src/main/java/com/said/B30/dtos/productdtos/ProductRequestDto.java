package com.said.B30.dtos.productdtos;

public record ProductRequestDto(String description,
                                String productionProcessNote,
                                Double materialValue,
                                Double externalServiceValue,
                                Double establishedValue) {
}
