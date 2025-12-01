package com.said.B30.dtos.productdtos;

import com.said.B30.infrastructure.enums.ProductStatus;

import java.time.LocalDate;

public record ProductSoldResponseDto(Long id,
                                     String description,
                                     LocalDate productionDate,
                                     LocalDate saleDate,
                                     Double establishedValue,
                                     ProductStatus productStatus,
                                     String invoice,
                                     Long clientId) {
}
