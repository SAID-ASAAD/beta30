package com.said.B30.businessrules.helpers.sellhelpers;

import com.said.B30.dtos.selldtos.SellUpdateRequestDto;
import com.said.B30.infrastructure.entities.Sell;
import org.springframework.stereotype.Component;

@Component
public class SellUpdate {

    public void updateSellData(SellUpdateRequestDto dto, Sell entity) {
        if (dto.quantity() != null) {
            entity.setQuantity(dto.quantity());
        }
        if (dto.unitValue() != null) {
            entity.setUnitValue(dto.unitValue());
        }
        if (dto.saleDate() != null) {
            entity.setSaleDate(dto.saleDate());
        }
        if (dto.paymentStatus() != null) {
            entity.setPaymentStatus(dto.paymentStatus());
        }
        if (entity.getQuantity() != null && entity.getUnitValue() != null) {
            entity.setTotalValue(entity.getQuantity() * entity.getUnitValue());
        }
    }
}
