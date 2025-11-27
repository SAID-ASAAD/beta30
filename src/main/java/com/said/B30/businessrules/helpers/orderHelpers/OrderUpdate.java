package com.said.B30.businessrules.helpers.orderHelpers;

import com.said.B30.dtos.orderdtos.OrderUpdateRequestDto;
import com.said.B30.infrastructure.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderUpdate {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    void updateOrderData(OrderUpdateRequestDto orderUpdateRequest, @MappingTarget Order orderEntity);
}
