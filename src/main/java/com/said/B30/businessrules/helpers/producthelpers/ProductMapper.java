package com.said.B30.businessrules.helpers.producthelpers;

import com.said.B30.dtos.productdtos.ProductFullResponseDto;
import com.said.B30.dtos.productdtos.ProductRequestDto;
import com.said.B30.dtos.productdtos.ProductResponseDto;
import com.said.B30.dtos.productdtos.ProductSoldResponseDto;
import com.said.B30.dtos.productdtos.ProductUpdateRequestDto;
import com.said.B30.infrastructure.entities.Product;
import com.said.B30.infrastructure.entities.Sell;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ProductMapper {

    @Mapping(target = "sales", ignore = true)
    @Mapping(target = "totalValue", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productionDate", ignore = true)
    @Mapping(target = "productStatus", ignore = true)
    Product toEntity(ProductRequestDto productRequest);

    ProductResponseDto toResponse(Product productEntity);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.description", target = "description")
    @Mapping(source = "product.productStatus", target = "productStatus")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "quantity", target = "quantitySold")
    ProductSoldResponseDto toSoldResponse(Sell sellEntity);

    ProductFullResponseDto toFullResponse(Product productEntity);
    
    ProductUpdateRequestDto toUpdateRequest(ProductFullResponseDto responseDto);

}
