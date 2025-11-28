package com.said.B30.dtos.orderdtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.said.B30.infrastructure.enums.Category;
import com.said.B30.infrastructure.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderUpdateResponseDto(Long id,
                                     Category category,
                                     String description,
                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
                                     LocalDateTime orderDate,
                                     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
                                     LocalDateTime deliveryDate,
                                     Double establishedValue,
                                     String invoice,
                                     String productionProcessNote,
                                     OrderStatus orderStatus,
                                     Long clientId) {
}
