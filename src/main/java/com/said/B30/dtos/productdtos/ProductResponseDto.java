package com.said.B30.dtos.productdtos;

import com.said.B30.infrastructure.enums.ProductStatus;

import java.time.LocalDate;

public record ProductResponseDto(Long id,
                                 String description,
                                 String productionProcessNote,
                                 LocalDate productionDate,
                                 Double materialValue,
                                 Double externalServiceValue,
                                 Double establishedValue,
                                 ProductStatus productStatus) {
}
