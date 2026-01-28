package com.said.B30.dtos.selldtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.said.B30.infrastructure.enums.PaymentStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SellUpdateRequestDto(
        @NotNull(message = "A quantidade vendida é obrigatória.")
        @Min(1)
        Integer quantity,
        @NotNull(message = "O valor unitário da venda é obrigatório.")
        Double unitValue,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        @NotNull(message = "A data da venda é obrigatória.")
        LocalDate saleDate,
        @NotNull(message = "O status do pagamento é obrigatório.")
        PaymentStatus paymentStatus
) {}
