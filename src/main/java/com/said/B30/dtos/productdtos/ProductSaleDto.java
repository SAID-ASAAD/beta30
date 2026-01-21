package com.said.B30.dtos.productdtos;

import jakarta.validation.constraints.NotNull;

public record ProductSaleDto(@NotNull(message = "O ID do cliente é obrigatório")
                             Long clientId,
                             @NotNull(message = "A quantidade é obrigatória")
                             Integer quantity,
                             @NotNull(message = "O valor cobrado é obrigatório")
                             Double establishedValue,
                             Double totalValue,
                             Double initialPayment
                             ) {
}
