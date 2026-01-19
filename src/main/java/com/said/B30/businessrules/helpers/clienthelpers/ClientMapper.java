package com.said.B30.businessrules.helpers.clienthelpers;

import com.said.B30.dtos.clientdtos.ClientRequestDto;
import com.said.B30.dtos.clientdtos.ClientResponseDto;
import com.said.B30.dtos.clientdtos.ClientUpdateRequestDto;
import com.said.B30.dtos.clientdtos.ClientUpdateResponseDto;
import com.said.B30.infrastructure.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ClientMapper {

    @Mapping(target = "products", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    Client toEntity(ClientRequestDto clientRequest);
    ClientResponseDto toResponse(Client clientEntity);
    ClientUpdateResponseDto toUpdateResponseDto(Client clientEntity);
    
    ClientUpdateRequestDto toUpdateRequest(ClientResponseDto responseDto);
}
