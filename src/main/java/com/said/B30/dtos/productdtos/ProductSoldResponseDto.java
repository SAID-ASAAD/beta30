package com.said.B30.dtos.productdtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.said.B30.infrastructure.enums.ProductStatus;

import java.time.LocalDate;

public record ProductSoldResponseDto(Long productId,
                                     String description,
                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                     LocalDate saleDate,
                                     Double unitValue,
                                     Double totalValue,
                                     Integer quantitySold,
                                     ProductStatus productStatus,
                                     Long clientId) {
}
