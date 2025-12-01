package com.said.B30.businessrules.helpers.orderhelpers;

import com.said.B30.dtos.orderdtos.OrderRequestDto;
import com.said.B30.dtos.orderdtos.OrderResponseDto;
import com.said.B30.dtos.orderdtos.OrderUpdateResponseDto;
import com.said.B30.infrastructure.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface OrderMapper {

    @Mapping(target = "materialValue", ignore = true)
    @Mapping(target = "externalServiceValue", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "productionProcessNote", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "client", ignore = true)
    Order toEntity(OrderRequestDto orderRequest);

    @Mapping(source = "client.id", target = "clientId")
    OrderResponseDto toResponseDto(Order orderEntity);

    @Mapping(source = "client.id", target = "clientId")
    OrderUpdateResponseDto toUpdateResponseDto(Order orderEntity);

}
