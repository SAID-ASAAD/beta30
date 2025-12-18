package com.said.B30.businessrules.exceptions;

public class PaymentNotAllowedException extends RuntimeException {
    public PaymentNotAllowedException(String message) {
        super(message);
    }
}
