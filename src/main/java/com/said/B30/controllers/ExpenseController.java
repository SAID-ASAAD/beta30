package com.said.B30.controllers;

import com.said.B30.businessrules.services.ExpenseService;
import com.said.B30.dtos.expensedtos.ExpenseRequestDto;
import com.said.B30.dtos.expensedtos.ExpenseResponseDto;
import com.said.B30.dtos.expensedtos.ExpenseUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/finances/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @GetMapping
    public ModelAndView getAllExpenses() {
        ModelAndView mv = new ModelAndView("finances/expenses-page");
        mv.addObject("expenses", expenseService.findAllExpenses());
        return mv;
    }

    @GetMapping("/register")
    public ModelAndView getRegisterExpensePage() {
        ModelAndView mv = new ModelAndView("finances/expense-register-form");
        mv.addObject("expenseRequestDto", new ExpenseRequestDto(null, null, null));
        return mv;
    }

    @PostMapping("/register")
    public ModelAndView registerExpense(@Valid ExpenseRequestDto expenseRequest, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("finances/expense-register-form");
        }
        expenseService.createExpense(expenseRequest);
        return new ModelAndView("redirect:/finances/expenses");
    }
    
    @GetMapping("/edit/{id}")
    public ModelAndView getEditExpenseForm(@PathVariable Long id) {
        ExpenseResponseDto expense = expenseService.findExpenseById(id);
        ExpenseUpdateRequestDto updateDto = expenseService.getExpenseForUpdate(id);
        
        ModelAndView mv = new ModelAndView("finances/expense-update-form");
        mv.addObject("expense", expense);
        mv.addObject("expenseUpdateRequestDto", updateDto);
        return mv;
    }
    
    @PutMapping("/edit/{id}")
    public ModelAndView updateExpense(@PathVariable Long id, @Valid ExpenseUpdateRequestDto updateDto, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("finances/expense-update-form");
            mv.addObject("expense", expenseService.findExpenseById(id));
            return mv;
        }
        expenseService.updateExpenseData(id, updateDto);
        return new ModelAndView("redirect:/finances/expenses");
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteExpenseById(@PathVariable Long id){
        expenseService.deleteExpenseById(id);
        return new ModelAndView("redirect:/finances/expenses");
    }
    
    @GetMapping("/list")
    @ResponseBody
    public List<ExpenseResponseDto> findAllExpensesApi(){
        return expenseService.findAllExpenses();
    }
}
