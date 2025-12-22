package com.said.B30.controllers;

import com.said.B30.businessrules.services.FinancialStatisticsService;
import com.said.B30.dtos.paymentdtos.PaymentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/estatisticas_financeiras")
@RequiredArgsConstructor
public class FinancialStatisticsController {

    private final FinancialStatisticsService financialStatisticsService;

    @GetMapping("/todos_pagamentos")
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments(){
        return ResponseEntity.ok(financialStatisticsService.getAllPayments());
    }

    @GetMapping("/total_recebido")
    public ResponseEntity<Double> getTotalRevenue(){
        return ResponseEntity.ok(financialStatisticsService.getTotalRevenue());
    }

    @GetMapping("/pagamentos_por_data")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate){
        return ResponseEntity.ok(financialStatisticsService.getPaymentsByDateRange(startDate, endDate));
    }

    @GetMapping("/recebido_por_data")
    public ResponseEntity<Double> getRevenueByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate){
        return ResponseEntity.ok(financialStatisticsService.getRevenueByDateRange(startDate, endDate));
    }

    @GetMapping("/pagamentos_por_cliente")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByClientId(@RequestParam Long clientId){
        return ResponseEntity.ok(financialStatisticsService.getPaymentsByClientId(clientId));
    }

    @GetMapping("/recebido_por_cliente")
    public ResponseEntity<Double> getRevenueByClientId(@RequestParam Long clientId){
        return ResponseEntity.ok(financialStatisticsService.getRevenueByClientId(clientId));
    }
}
