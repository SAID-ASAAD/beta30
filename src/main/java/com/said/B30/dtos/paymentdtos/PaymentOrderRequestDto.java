package com.said.B30.dtos.paymentdtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PaymentOrderRequestDto(
                                @NotNull(message = "O VALOR do pagamento deve ser informado.")
                                Double amount,
                                @NotNull(message = "O ID DO PEDIDO de referência ao pagamento deve ser informado.")
                                Long orderId,
                                @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
                                @NotNull(message = "A DATA DO PAGAMENTO deve ser informada.")
                                LocalDate paymentDate) {
}
