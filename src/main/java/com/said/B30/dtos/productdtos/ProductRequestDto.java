package com.said.B30.dtos.productdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequestDto(@NotBlank(message = "A DESCRIÇÃO do produto deve ser informada.")
                                String description,
                                String productionProcessNote,
                                @NotNull(message = "O VALOR gasto em MATERIAL no produto deve ser informado.")
                                Double materialValue,
                                @NotNull(message = "O VALOR gasto em SERVIÇOS DE TERCEIROS no produto deve ser informado.")
                                Double externalServiceValue,
                                @NotNull(message = "O VALOR SUGERIDO (pré-estabelecido) ao produto deve ser informado.")
                                Double preEstablishedValue) {
}
