package com.said.B30.businessrules.helpers.expensehelpers;

import com.said.B30.dtos.expensedtos.ExpenseUpdateRequestDto;
import com.said.B30.infrastructure.entities.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseUpdate {

    public void updateExpenseDate(ExpenseUpdateRequestDto dto, Expense entity){
        if(dto.description() != null && !dto.description().isBlank()){
            entity.setDescription(dto.description());
        }
        if(dto.amount() != null){
            entity.setAmount(dto.amount());
        }
        if(dto.referenceMonth() != null){
            entity.setReferenceMonth(dto.referenceMonth());
        }
        if(dto.paymentDate() != null){
            entity.setPaymentDate(dto.paymentDate());
        }
    }
}
