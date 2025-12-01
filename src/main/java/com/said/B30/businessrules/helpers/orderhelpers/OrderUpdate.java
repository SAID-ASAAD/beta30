package com.said.B30.businessrules.helpers.orderhelpers;

import com.said.B30.dtos.orderdtos.OrderUpdateRequestDto;
import com.said.B30.infrastructure.entities.Order;
import com.said.B30.infrastructure.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderUpdate {

    private final ClientRepository clientRepository;

    public void updateOrderData(OrderUpdateRequestDto dto, Order entity){
        if (dto.category() != null){
            entity.setCategory(dto.category());
        }
        if (dto.description() != null && !dto.description().isBlank()){
            entity.setDescription(dto.description());
        }
        if (dto.orderDate() != null){
            entity.setOrderDate(dto.orderDate());
        }
        if (dto.deliveryDate() != null){
            entity.setDeliveryDate(dto.deliveryDate());
        }
        if (dto.establishedValue() != null){
            entity.setEstablishedValue(dto.establishedValue());
        }
        if (dto.clientId() != null){
            var client = clientRepository.getReferenceById(dto.clientId());
            entity.setClient(client);
        }
        if (dto.invoice() != null){
            entity.setInvoice(dto.invoice());
        }
        if (dto.productionProcessNote() != null){
            entity.setProductionProcessNote(dto.productionProcessNote());
        }
        if (dto.orderStatus() != null){
            entity.setOrderStatus(dto.orderStatus());
        }
    }
}
