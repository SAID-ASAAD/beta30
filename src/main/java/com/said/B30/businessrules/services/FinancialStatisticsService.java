package com.said.B30.businessrules.services;

import com.said.B30.businessrules.helpers.paymenthelpers.PaymentMapper;
import com.said.B30.dtos.paymentdtos.PaymentResponseDto;
import com.said.B30.infrastructure.entities.Payment;
import com.said.B30.infrastructure.repositories.OrderRepository;
import com.said.B30.infrastructure.repositories.PaymentRepository;
import com.said.B30.infrastructure.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialStatisticsService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PaymentMapper mapper;

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Double getTotalRevenue() {
        return paymentRepository.totalRevenue();
    }

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Payment> payments = paymentRepository.findByPaymentDateBetween(startDate, endDate);
        return payments.stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Double getRevenueByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.totalRevenueByDateRange(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getPaymentsByClientId(Long clientId){
        List<Payment> payments = paymentRepository.findPaymentsByClientId(clientId);
        return payments.stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Double getRevenueByClientId(Long clientId){
        return paymentRepository.totalRevenueByClientId(clientId);
    }

    @Transactional(readOnly = true)
    public Double getTotalReceivable() {
        Double ordersReceivable = orderRepository.totalReceivable();
        Double productsReceivable = productRepository.totalReceivable();
        
        if (ordersReceivable == null) ordersReceivable = 0.0;
        if (productsReceivable == null) productsReceivable = 0.0;

        return ordersReceivable + productsReceivable;
    }

    @Transactional(readOnly = true)
    public Double getReceivableByDateRange(LocalDate startDate, LocalDate endDate) {
        Double receivable = orderRepository.totalReceivableByDateRange(startDate, endDate);
        return receivable != null ? receivable : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getReceivableByClientId(Long clientId) {
        Double ordersReceivable = orderRepository.totalReceivableByClientId(clientId);
        Double productsReceivable = productRepository.totalReceivableByClientId(clientId);

        if (ordersReceivable == null) ordersReceivable = 0.0;
        if (productsReceivable == null) productsReceivable = 0.0;

        return ordersReceivable + productsReceivable;
    }

    @Transactional(readOnly = true)
    public Double getReceivableByOrderId(Long orderId) {
        Double receivable = orderRepository.getReceivableAmount(orderId);
        return receivable != null ? receivable : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getReceivableByProductId(Long productId) {
        Double receivable = productRepository.getReceivableAmount(productId);
        return receivable != null ? receivable : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getProfitByOrderId(Long orderId) {
        Double profit = orderRepository.getProfitByOrderId(orderId);
        return profit != null ? profit : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getProfitByProductId(Long productId) {
        Double profit = productRepository.getProfitByProductId(productId);
        return profit != null ? profit : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getTotalOrdersProfit() {
        Double profit = orderRepository.getTotalProfit();
        return profit != null ? profit : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getTotalProductsProfit() {
        Double profit = productRepository.getTotalProfit();
        return profit != null ? profit : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getTotalProfit() {
        Double ordersProfit = orderRepository.getTotalProfit();
        Double productsProfit = productRepository.getTotalProfit();

        if (ordersProfit == null) ordersProfit = 0.0;
        if (productsProfit == null) productsProfit = 0.0;

        return ordersProfit + productsProfit;
    }

    @Transactional(readOnly = true)
    public Double getTotalProfitByDateRange(LocalDate startDate, LocalDate endDate) {
        Double ordersProfit = orderRepository.getTotalProfitByDateRange(startDate, endDate);
        Double productsProfit = productRepository.getTotalProfitByDateRange(startDate, endDate);

        if (ordersProfit == null) ordersProfit = 0.0;
        if (productsProfit == null) productsProfit = 0.0;

        return ordersProfit + productsProfit;
    }
}
