package com.said.B30.businessrules.helpers.sellhelpers;

import com.said.B30.dtos.selldtos.SellUpdateRequestDto;
import com.said.B30.infrastructure.entities.Sell;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SellMapper {

    SellUpdateRequestDto toUpdateRequest(Sell sellEntity);
}
