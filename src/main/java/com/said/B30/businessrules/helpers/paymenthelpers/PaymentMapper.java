package com.said.B30.businessrules.helpers.paymenthelpers;

import com.said.B30.dtos.paymentdtos.PaymentRequestDto;
import com.said.B30.dtos.paymentdtos.PaymentResponseDto;
import com.said.B30.infrastructure.entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PaymentMapper {

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "id", ignore = true)
    Payment toEntity(PaymentRequestDto paymentRequest);

    @Mapping(target = "orderId", source = "order.id")
    PaymentResponseDto toResponse(Payment paymentEntity);
}
