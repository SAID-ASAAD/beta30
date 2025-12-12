package com.said.B30.businessrules.helpers.producthelpers;

import com.said.B30.dtos.productdtos.ProductFullResponseDto;
import com.said.B30.dtos.productdtos.ProductRequestDto;
import com.said.B30.dtos.productdtos.ProductResponseDto;
import com.said.B30.dtos.productdtos.ProductSoldResponseDto;
import com.said.B30.infrastructure.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProductMapper {

    @Mapping(target = "establishedValue", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "productionDate", ignore = true)
    @Mapping(target = "productStatus", ignore = true)
    @Mapping(target = "saleDate", ignore = true)
    @Mapping(target = "client", ignore = true)
    Product toEntity(ProductRequestDto productRequest);

    ProductResponseDto toResponse(Product productEntity);

    @Mapping(source = "client.id", target = "clientId")
    ProductSoldResponseDto toSoldResponse(Product productEntity);

    @Mapping(source = "client.id", target = "clientId")
    ProductFullResponseDto toFullResponse(Product productEntity);


}
