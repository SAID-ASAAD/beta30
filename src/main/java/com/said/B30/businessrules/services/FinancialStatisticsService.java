package com.said.B30.businessrules.services;

import com.said.B30.businessrules.helpers.expensehelpers.ExpenseMapper;
import com.said.B30.businessrules.helpers.paymenthelpers.PaymentMapper;
import com.said.B30.dtos.expensedtos.ExpenseResponseDto;
import com.said.B30.dtos.paymentdtos.PaymentResponseDto;
import com.said.B30.dtos.paymentdtos.ProfitDetailDto;
import com.said.B30.dtos.paymentdtos.ReceivableDto;
import com.said.B30.infrastructure.entities.Expense;
import com.said.B30.infrastructure.entities.Order;
import com.said.B30.infrastructure.entities.Payment;
import com.said.B30.infrastructure.entities.Product;
import com.said.B30.infrastructure.entities.Sell;
import com.said.B30.infrastructure.repositories.ExpenseRepository;
import com.said.B30.infrastructure.repositories.OrderRepository;
import com.said.B30.infrastructure.repositories.PaymentRepository;
import com.said.B30.infrastructure.repositories.SellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinancialStatisticsService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final SellRepository sellRepository;
    private final ExpenseRepository expenseRepository;
    private final PaymentMapper paymentMapper;
    private final ExpenseMapper expenseMapper;

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(paymentMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Double getTotalRevenue() {
        return paymentRepository.totalRevenue();
    }

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Payment> payments = paymentRepository.findByPaymentDateBetween(startDate, endDate);
        return payments.stream().map(paymentMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Double getRevenueByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.totalRevenueByDateRange(startDate, endDate);
    }

    @Transactional(readOnly = true)
    public List<PaymentResponseDto> getPaymentsByClientId(Long clientId){
        List<Payment> payments = paymentRepository.findPaymentsByClientId(clientId);
        return payments.stream().map(paymentMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Double getRevenueByClientId(Long clientId){
        return paymentRepository.totalRevenueByClientId(clientId);
    }

    @Transactional(readOnly = true)
    public Double getTotalReceivable() {
        Double ordersReceivable = orderRepository.totalReceivable();
        Double sellsReceivable = sellRepository.totalReceivable();
        
        if (ordersReceivable == null) ordersReceivable = 0.0;
        if (sellsReceivable == null) sellsReceivable = 0.0;

        return ordersReceivable + sellsReceivable;
    }

    @Transactional(readOnly = true)
    public Double getReceivableByDateRange(LocalDate startDate, LocalDate endDate) {
        Double ordersReceivable = orderRepository.totalReceivableByDateRange(startDate, endDate);
        Double sellsReceivable = sellRepository.totalReceivableByDateRange(startDate, endDate);

        if (ordersReceivable == null) ordersReceivable = 0.0;
        if (sellsReceivable == null) sellsReceivable = 0.0;
        
        return ordersReceivable + sellsReceivable;
    }
    
    @Transactional(readOnly = true)
    public List<ReceivableDto> getReceivablesListByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Order> orders = orderRepository.findPendingOrdersByDateRange(startDate, endDate);
        List<Sell> sells = sellRepository.findPendingSellsByDateRange(startDate, endDate);
        
        List<ReceivableDto> receivables = new ArrayList<>();
        
        for (Order o : orders) {
            double paid = o.getPayments().stream().mapToDouble(Payment::getAmount).sum();
            double remaining = o.getEstablishedValue() - paid;
            receivables.add(new ReceivableDto(
                o.getId(),
                "Pedido",
                o.getDescription(),
                o.getClient().getName(),
                o.getDeliveryDate(),
                o.getEstablishedValue(),
                paid,
                remaining
            ));
        }
        
        for (Sell s : sells) {
            double paid = s.getPayments().stream().mapToDouble(Payment::getAmount).sum();
            double remaining = s.getTotalValue() - paid;
            receivables.add(new ReceivableDto(
                s.getId(),
                "Venda",
                s.getProduct().getDescription(),
                s.getClient().getName(),
                s.getSaleDate(),
                s.getTotalValue(),
                paid,
                remaining
            ));
        }
        
        return receivables;
    }

    @Transactional(readOnly = true)
    public Double getReceivableByClientId(Long clientId) {
        Double ordersReceivable = orderRepository.totalReceivableByClientId(clientId);
        Double sellsReceivable = sellRepository.totalReceivableByClientId(clientId);

        if (ordersReceivable == null) ordersReceivable = 0.0;
        if (sellsReceivable == null) sellsReceivable = 0.0;

        return ordersReceivable + sellsReceivable;
    }

    @Transactional(readOnly = true)
    public Double getReceivableByOrderId(Long orderId) {
        Double receivable = orderRepository.getReceivableAmount(orderId);
        return receivable != null ? receivable : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getReceivableBySellId(Long sellId) {
        Double receivable = sellRepository.getReceivableAmountBySellId(sellId);
        return receivable != null ? receivable : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getProfitByOrderId(Long orderId) {
        Double profit = orderRepository.getProfitByOrderId(orderId);
        return profit != null ? profit : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getProfitByProductId(Long productId) {
        Double profit = sellRepository.getProfitByProductId(productId);
        return profit != null ? profit : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getTotalOrdersProfit() {
        Double profit = orderRepository.getTotalProfit();
        return profit != null ? profit : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getTotalProductsProfit() {
        Double profit = sellRepository.getTotalProfit();
        return profit != null ? profit : 0.0;
    }

    @Transactional(readOnly = true)
    public Double getTotalGrossProfit() {
        Double ordersProfit = orderRepository.getTotalProfit();
        Double productsProfit = sellRepository.getTotalProfit();

        if (ordersProfit == null) ordersProfit = 0.0;
        if (productsProfit == null) productsProfit = 0.0;

        return ordersProfit + productsProfit;
    }

    @Transactional(readOnly = true)
    public Double getTotalGrossProfitByDateRange(LocalDate startDate, LocalDate endDate) {
        Double ordersProfit = orderRepository.getTotalProfitByDateRange(startDate, endDate);
        Double productsProfit = sellRepository.getTotalProfitByDateRange(startDate, endDate);

        if (ordersProfit == null) ordersProfit = 0.0;
        if (productsProfit == null) productsProfit = 0.0;

        return ordersProfit + productsProfit;
    }
    
    @Transactional(readOnly = true)
    public Double getTotalExpenses() {
        Double expenses = expenseRepository.totalExpenses();
        return expenses != null ? expenses : 0.0;
    }
    
    @Transactional(readOnly = true)
    public Double getTotalExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        Double expenses = expenseRepository.totalExpensesByDateRange(startDate, endDate);
        return expenses != null ? expenses : 0.0;
    }
    
    @Transactional(readOnly = true)
    public List<ExpenseResponseDto> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses = expenseRepository.findByReferenceMonthBetween(startDate, endDate);
        return expenses.stream().map(expenseMapper::toResponse).collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public Double getTotalNetProfit() {
        return getTotalRevenue() - getTotalExpenses();
    }
    
    @Transactional(readOnly = true)
    public Double getTotalNetProfitByDateRange(LocalDate startDate, LocalDate endDate) {
        Double revenue = getRevenueByDateRange(startDate, endDate);
        if (revenue == null) revenue = 0.0;
        
        Double expenses = getTotalExpensesByDateRange(startDate, endDate);
        
        return revenue - expenses;
    }
    
    @Transactional(readOnly = true)
    public List<ProfitDetailDto> getProfitDetailsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Order> orders = orderRepository.findAllByDeliveryDateBetween(startDate, endDate);
        List<Sell> sells = sellRepository.findAllBySaleDateBetween(startDate, endDate);
        
        List<ProfitDetailDto> details = new ArrayList<>();
        
        for (Order o : orders) {
            double paid = o.getPayments().stream().mapToDouble(Payment::getAmount).sum();
            double cost = (o.getMaterialValue() != null ? o.getMaterialValue() : 0.0) + 
                          (o.getExternalServiceValue() != null ? o.getExternalServiceValue() : 0.0);
            double profit = paid - cost;
            
            details.add(new ProfitDetailDto(
                o.getId(),
                "Pedido",
                o.getDescription(),
                paid,
                cost,
                profit
            ));
        }
        
        for (Sell s : sells) {
            double paid = s.getPayments().stream().mapToDouble(Payment::getAmount).sum();
            
            Product p = s.getProduct();
            double totalCost = (p.getMaterialValue() != null ? p.getMaterialValue() : 0.0) + 
                               (p.getExternalServiceValue() != null ? p.getExternalServiceValue() : 0.0);
            
            int totalSold = p.getSales().stream().mapToInt(Sell::getQuantity).sum();
            int totalStock = p.getQuantity() + totalSold;
            
            double unitCost = totalStock > 0 ? totalCost / totalStock : 0.0;
            double cost = s.getQuantity() * unitCost;
            
            double profit = paid - cost;
            
            details.add(new ProfitDetailDto(
                s.getId(),
                "Venda",
                p.getDescription(),
                paid,
                cost,
                profit
            ));
        }
        
        return details;
    }
}
