package com.said.B30.infrastructure.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {
    @Override
    public String convertToDatabaseColumn(Category attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getDescription();
    }

    @Override
    public Category convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        for (Category category : Category.values()){
            if(category.getDescription().equals(dbData))
                return category;
        }
        throw new IllegalArgumentException("Categoria inválida: " + dbData);
    }
}
