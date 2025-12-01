package com.said.B30.dtos.productdtos;

import com.said.B30.infrastructure.enums.ProductStatus;

import java.time.LocalDate;

public record ProductUpdateRequestDto(
                                      String description,
                                      String productionProcessNote,
                                      LocalDate productionDate,
                                      LocalDate saleDate,
                                      Double materialValue,
                                      Double externalServiceValue,
                                      Double establishedValue,
                                      ProductStatus productStatus,
                                      String invoice,
                                      Long clientId) {
}
