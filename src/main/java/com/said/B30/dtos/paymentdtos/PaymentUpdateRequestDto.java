package com.said.B30.dtos.paymentdtos;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record PaymentUpdateRequestDto(Double amount,
                                      @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                      LocalDate paymentDate) {
}
