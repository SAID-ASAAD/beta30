package com.said.B30.businessrules.helpers.expensehelpers;

import com.said.B30.dtos.expensedtos.ExpenseRequestDto;
import com.said.B30.dtos.expensedtos.ExpenseResponseDto;
import com.said.B30.infrastructure.entities.Expense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ExpenseMapper {

    @Mapping(target = "paymentDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    Expense toEntity(ExpenseRequestDto expenseRequest);

    ExpenseResponseDto toResponse(Expense expenseEntity);
}
