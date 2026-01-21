package com.said.B30.dtos.productdtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.said.B30.infrastructure.enums.ProductStatus;

import java.time.LocalDate;

public record ProductResponseDto(Long id,
                                 Integer quantity,
                                 String description,
                                 String productionProcessNote,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                 LocalDate productionDate,
                                 Double materialValue,
                                 Double externalServiceValue,
                                 Double preEstablishedValue,
                                 ProductStatus productStatus) {
}
