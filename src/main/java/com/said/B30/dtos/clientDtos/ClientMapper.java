package com.said.B30.dtos.clientDtos;

import com.said.B30.infrastructure.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    Client toEntity(ClientRequestDto clientRequest);
    ClientResponseDto toResponse(Client clientEntity);
}
