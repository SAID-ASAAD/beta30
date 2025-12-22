package com.said.B30.dtos.paymentdtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record PaymentResponseDto(Long id,
                                 Double amount,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                 LocalDate paymentDate,
                                 Long orderId,
                                 Long productId) {
}
