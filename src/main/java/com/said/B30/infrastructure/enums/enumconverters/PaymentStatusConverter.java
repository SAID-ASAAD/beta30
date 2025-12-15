package com.said.B30.infrastructure.enums.enumconverters;

import com.said.B30.infrastructure.enums.PaymentStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PaymentStatusConverter implements AttributeConverter<PaymentStatus, String>{
    @Override
    public String convertToDatabaseColumn(PaymentStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDescription();
    }

    @Override
    public PaymentStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (PaymentStatus status : PaymentStatus.values()){
            if(status.getDescription().equals(dbData))
                return status;
        }
        throw new IllegalArgumentException("Descrição de Status do pedido inválida: " + dbData);
    }
}
