package com.said.B30.dtos.orderdtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.said.B30.infrastructure.enums.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record OrderRequestDto(
                              @NotNull(message = "A CATEGORIA do pedido deve ser informada.")
                              Category category,
                              @NotBlank(message = "A DESCRIÇÃO do pedido deve ser informada.")
                              String description,
                              @NotNull(message = "A DATA DE ENTREGA do pedido deve ser informada.")
                              @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
                              LocalDateTime deliveryDate,
                              @NotNull(message = "O VALOR do pedido deve ser informado.")
                              Double establishedValue,
                              @NotNull(message = "O ID DO CLIENTE do pedido deve ser informada.")
                              Long clientId) {

}
