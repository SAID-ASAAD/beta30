package com.said.B30.dtos.expensedtos;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ExpenseResponseDto(Long id,
                                 String description,
                                 Double amount,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yyyy")
                                 LocalDate referenceMonth,
                                 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                 LocalDate paymentDate) {
}
