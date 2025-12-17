package com.said.B30.businessrules.helpers.paymenthelpers;

import com.said.B30.dtos.paymentdtos.PaymentUpdateRequestDto;
import com.said.B30.infrastructure.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentUpdate {

    public void updatePaymentData(PaymentUpdateRequestDto dto, Payment entity){
        if(dto.amount() != null){
            entity.setAmount(dto.amount());
        }
        if(dto.paymentDate() != null){
            entity.setPaymentDate(dto.paymentDate());
        }
    }
}
