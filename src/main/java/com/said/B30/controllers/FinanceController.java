package com.said.B30.controllers;

import com.said.B30.businessrules.services.FinancialStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/finances")
@RequiredArgsConstructor
public class FinanceController {

    private final FinancialStatisticsService financialStatisticsService;

    @GetMapping
    public String financeHome() {
        return "finances/finance-home";
    }

    @GetMapping("/payments")
    public ModelAndView getAllPayments() {
        ModelAndView mv = new ModelAndView("finances/payments-page");
        mv.addObject("payments", financialStatisticsService.getAllPayments());
        return mv;
    }
}
