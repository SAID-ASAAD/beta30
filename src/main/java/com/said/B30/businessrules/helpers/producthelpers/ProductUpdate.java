package com.said.B30.businessrules.helpers.producthelpers;

import com.said.B30.dtos.productdtos.ProductUpdateRequestDto;
import com.said.B30.infrastructure.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdate {


    public void updateProductData(ProductUpdateRequestDto dto, Product entity){
        if(dto.description() != null && !dto.description().isBlank()){
            entity.setDescription(dto.description());
        }
        if(dto.quantity() != null){
            entity.setQuantity(dto.quantity());
        }
        if(dto.productionProcessNote() != null){
            entity.setProductionProcessNote(dto.productionProcessNote());
        }
        if(dto.productionDate() != null){
            entity.setProductionDate(dto.productionDate());
        }
        if (dto.materialValue() != null){
            entity.setMaterialValue(dto.materialValue());
        }
        if(dto.externalServiceValue() != null){
            entity.setExternalServiceValue(dto.externalServiceValue());
        }
        if(dto.preEstablishedValue() != null){
            entity.setPreEstablishedValue(dto.preEstablishedValue());
        }
        if(dto.productStatus() != null){
            entity.setProductStatus(dto.productStatus());
        }
    }
}
