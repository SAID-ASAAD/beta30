package com.said.B30.controllers;

import com.said.B30.businessrules.services.ExpenseService;
import com.said.B30.dtos.expensedtos.ExpenseRequestDto;
import com.said.B30.dtos.expensedtos.ExpenseResponseDto;
import com.said.B30.dtos.expensedtos.ExpenseUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/despesas")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponseDto> registerPayment(@Valid @RequestBody ExpenseRequestDto expenseRequest){
        var obj = expenseService.createExpense(expenseRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.id()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDto>> findAllExpenses(){
        return ResponseEntity.ok(expenseService.findAllExpenses());
    }

    @PutMapping("/{id}")
    ResponseEntity<ExpenseResponseDto> updateExpenseData(@PathVariable Long id, @Valid @RequestBody ExpenseUpdateRequestDto expenseUpdateRequest){
        return ResponseEntity.ok(expenseService.updateExpenseData(id, expenseUpdateRequest));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteExpenseById(@PathVariable Long id){
        expenseService.deleteExpenseById(id);
        return ResponseEntity.accepted().build();
    }
}
