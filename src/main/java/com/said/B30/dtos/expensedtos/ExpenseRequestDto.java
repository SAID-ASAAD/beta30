package com.said.B30.dtos.expensedtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ExpenseRequestDto(
                                @NotBlank(message = "A DESCRIÇÃO da despesa deve ser informada.")
                                String description,
                                @NotNull(message = "O VALOR da despesa deve ser informado.")
                                Double amount,
                                @NotNull(message = "O MÊS DE REFERÊNCIA da despesa deve ser informado.")
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                LocalDate referenceMonth) {
}
