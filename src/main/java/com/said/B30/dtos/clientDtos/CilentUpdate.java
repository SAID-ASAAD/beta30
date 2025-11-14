package com.said.B30.dtos.clientDtos;

import com.said.B30.infrastructure.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CilentUpdate {

    void updateClientData(ClientRequestDto clientRequest, @MappingTarget Client clientEntity);
}
