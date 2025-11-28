package com.said.B30.infrastructure.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProductConverter implements AttributeConverter<ProductStatus, String> {

    @Override
    public String convertToDatabaseColumn(ProductStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDescription();    }

    @Override
    public ProductStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (ProductStatus productStatus : ProductStatus.values()){
            if(productStatus.getDescription().equals(dbData))
                return productStatus;
        }
        throw new IllegalArgumentException("Status de produto inválida: " + dbData);
    }
}
