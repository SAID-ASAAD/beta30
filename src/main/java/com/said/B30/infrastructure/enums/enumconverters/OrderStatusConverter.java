package com.said.B30.infrastructure.enums.enumconverters;

import com.said.B30.infrastructure.enums.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDescription();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (OrderStatus status : OrderStatus.values()){
            if(status.getDescription().equals(dbData))
                return status;
        }
        throw new IllegalArgumentException("Descrição de Status do pedido inválida: " + dbData);
    }
}
