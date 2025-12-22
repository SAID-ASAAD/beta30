package com.said.B30.businessrules.services;

import com.said.B30.businessrules.helpers.paymenthelpers.PaymentMapper;
import com.said.B30.dtos.paymentdtos.PaymentResponseDto;
import com.said.B30.infrastructure.entities.Payment;
import com.said.B30.infrastructure.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FinancialStatisticsService {

    private final PaymentRepository paymentRepository;
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
}
