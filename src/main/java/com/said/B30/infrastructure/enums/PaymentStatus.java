package com.said.B30.infrastructure.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {

    PENDING_DEPOSIT("sinal_pendente"),
    DEPOSIT_PAID("sinal_pago"),
    PENDING_PAYMENT("pagamento_pendente"),
    PAYMENT_OK("pagamento_OK");

    private final String description;

    PaymentStatus(String description){
        this.description = description;
    }
}
