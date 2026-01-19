package com.said.B30.infrastructure.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {

    PENDING_DEPOSIT("SINAL_PENDENTE"),
    DEPOSIT_PAID("SINAL_PAGO"),
    PENDING_PAYMENT("PAGAMENTO_PENDENTE"),
    PAYMENT_OK("PAGAMENTO_OK");

    private final String description;

    PaymentStatus(String description){
        this.description = description;
    }
}
