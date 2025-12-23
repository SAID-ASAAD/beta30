package com.said.B30.businessrules.helpers.producthelpers;

import com.said.B30.dtos.productdtos.ProductUpdateRequestDto;
import com.said.B30.infrastructure.entities.Product;
import com.said.B30.infrastructure.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductUpdate {

    private final ClientRepository clientRepository;

    public void updateProductData(ProductUpdateRequestDto dto, Product entity){
        if(dto.description() != null && !dto.description().isBlank()){
            entity.setDescription(dto.description());
        }
        if(dto.productionProcessNote() != null && !dto.productionProcessNote().isBlank()){
            entity.setProductionProcessNote(dto.productionProcessNote());
        }
        if(dto.productionDate() != null){
            entity.setProductionDate(dto.productionDate());
        }
        if(dto.saleDate() != null){
            entity.setSaleDate(dto.saleDate());
        }
        if (dto.materialValue() != null){
            entity.setMaterialValue(dto.materialValue());
        }
        if(dto.externalServiceValue() != null){
            entity.setExternalServiceValue(dto.externalServiceValue());
        }
        if(dto.establishedValue() != null){
            entity.setEstablishedValue(dto.establishedValue());
        }
        if(dto.productStatus() != null){
            entity.setProductStatus(dto.productStatus());
        }
        if(dto.invoice() != null && !dto.invoice().isBlank()){
            entity.setInvoice(dto.invoice());
        }
        if(dto.clientId() != null){
            var client = clientRepository.getReferenceById(dto.clientId());
            entity.setClient(client);
        }
    }
}
