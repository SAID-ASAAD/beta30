package com.said.B30.businessrules.helpers.paymenthelpers;

import com.said.B30.dtos.paymentdtos.*;
import com.said.B30.infrastructure.entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PaymentMapper {

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "sell", ignore = true)
    @Mapping(target = "id", ignore = true)
    Payment toEntity(PaymentOrderRequestDto paymentRequest);

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "sell", ignore = true)
    @Mapping(target = "id", ignore = true)
    Payment toEntity(PaymentProductRequestDto paymentRequest);

    @Mapping(target = "orderId", source = "order.id")
    PaymentOrderResponseDto toResponsePO(Payment paymentEntity);

    @Mapping(target = "sellId", source = "sell.id")
    PaymentProductResponseDto toResponsePP(Payment paymentEntity);

    @Mapping(target = "sellId", source = "sell.id")
    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productId", source = "sell.product.id")
    @Mapping(target = "clientName", source = "paymentEntity", qualifiedByName = "getClientName")
    PaymentResponseDto toResponse(Payment paymentEntity);

    @Named("getClientName")
    default String getClientName(Payment payment) {
        if (payment.getOrder() != null && payment.getOrder().getClient() != null) {
            return payment.getOrder().getClient().getName();
        }
        if (payment.getSell() != null && payment.getSell().getClient() != null) {
            return payment.getSell().getClient().getName();
        }
        return null;
    }
}
