package com.said.B30.dtos.productdtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.said.B30.infrastructure.enums.ProductStatus;

import java.time.LocalDate;

public record ProductSoldResponseDto(Long id,
                                     String description,
                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                     LocalDate productionDate,
                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                     LocalDate saleDate,
                                     Double establishedValue,
                                     ProductStatus productStatus,
                                     String invoice,
                                     Long clientId) {
}
