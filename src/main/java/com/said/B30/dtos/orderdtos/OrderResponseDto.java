package com.said.B30.dtos.orderdtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.said.B30.infrastructure.enums.Category;
import com.said.B30.infrastructure.enums.OrderStatus;
import com.said.B30.infrastructure.enums.PaymentStatus;

import java.time.LocalDate;

public record OrderResponseDto(Long id,
                               Category category,
                               String description,
                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                               LocalDate orderDate,
                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                               LocalDate deliveryDate,
                               Double establishedValue,
                               PaymentStatus paymentStatus,
                               OrderStatus orderStatus,
                               Long clientId) {
}
