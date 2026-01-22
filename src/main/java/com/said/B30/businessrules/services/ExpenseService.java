package com.said.B30.businessrules.services;

import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
import com.said.B30.businessrules.helpers.expensehelpers.ExpenseMapper;
import com.said.B30.businessrules.helpers.expensehelpers.ExpenseUpdate;
import com.said.B30.dtos.expensedtos.ExpenseRequestDto;
import com.said.B30.dtos.expensedtos.ExpenseResponseDto;
import com.said.B30.dtos.expensedtos.ExpenseUpdateRequestDto;
import com.said.B30.infrastructure.entities.Expense;
import com.said.B30.infrastructure.repositories.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper mapper;
    private final ExpenseUpdate expenseUpdate;

    public ExpenseResponseDto createExpense(ExpenseRequestDto expenseRequest){
        return mapper.toResponse(expenseRepository.saveAndFlush(mapper.toEntity(expenseRequest)));
    }

    public List<ExpenseResponseDto> findAllExpenses(){
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream().map(mapper::toResponse).toList();
    }
    
    public ExpenseResponseDto findExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return mapper.toResponse(expense);
    }

    public ExpenseUpdateRequestDto getExpenseForUpdate(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new ExpenseUpdateRequestDto(expense.getDescription(), expense.getAmount(), expense.getReferenceMonth(), expense.getPaymentDate());
    }

    public ExpenseResponseDto updateExpenseData(Long id, ExpenseUpdateRequestDto expenseUpdateRequest){
        if(!expenseRepository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }
        var expense = expenseRepository.getReferenceById(id);
        expenseUpdate.updateExpenseDate(expenseUpdateRequest, expense);
        return mapper.toResponse(expenseRepository.saveAndFlush(expense));
    }

    public void deleteExpenseById(Long id){
        if(!expenseRepository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }
        expenseRepository.deleteById(id);
    }
}
