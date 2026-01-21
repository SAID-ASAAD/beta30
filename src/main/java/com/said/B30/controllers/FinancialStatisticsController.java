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

    @GetMapping("/total_a_receber")
    public ResponseEntity<Double> getTotalReceivable(){
        return ResponseEntity.ok(financialStatisticsService.getTotalReceivable());
    }

    @GetMapping("/a_receber_por_data")
    public ResponseEntity<Double> getReceivableByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate){
        return ResponseEntity.ok(financialStatisticsService.getReceivableByDateRange(startDate, endDate));
    }

    @GetMapping("/a_receber_por_cliente")
    public ResponseEntity<Double> getReceivableByClientId(@RequestParam Long clientId){
        return ResponseEntity.ok(financialStatisticsService.getReceivableByClientId(clientId));
    }

    @GetMapping("/a_receber_por_pedido")
    public ResponseEntity<Double> getReceivableByOrderId(@RequestParam Long orderId){
        return ResponseEntity.ok(financialStatisticsService.getReceivableByOrderId(orderId));
    }

    @GetMapping("/a_receber_por_venda")
    public ResponseEntity<Double> getReceivableBySellId(@RequestParam Long sellId){
        return ResponseEntity.ok(financialStatisticsService.getReceivableBySellId(sellId));
    }

    @GetMapping("/lucro_por_pedido")
    public ResponseEntity<Double> getProfitByOrderId(@RequestParam Long orderId){
        return ResponseEntity.ok(financialStatisticsService.getProfitByOrderId(orderId));
    }

    @GetMapping("/lucro_por_produto")
    public ResponseEntity<Double> getProfitByProductId(@RequestParam Long productId){
        return ResponseEntity.ok(financialStatisticsService.getProfitByProductId(productId));
    }

    @GetMapping("/lucro_total_pedidos")
    public ResponseEntity<Double> getTotalOrdersProfit(){
        return ResponseEntity.ok(financialStatisticsService.getTotalOrdersProfit());
    }

    @GetMapping("/lucro_total_produtos")
    public ResponseEntity<Double> getTotalProductsProfit(){
        return ResponseEntity.ok(financialStatisticsService.getTotalProductsProfit());
    }

    @GetMapping("/lucro_total_geral")
    public ResponseEntity<Double> getTotalProfit(){
        return ResponseEntity.ok(financialStatisticsService.getTotalProfit());
    }

    @GetMapping("/lucro_por_data")
    public ResponseEntity<Double> getTotalProfitByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate){
        return ResponseEntity.ok(financialStatisticsService.getTotalProfitByDateRange(startDate, endDate));
    }
}
