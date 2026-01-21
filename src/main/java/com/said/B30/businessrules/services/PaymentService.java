package com.said.B30.businessrules.services;

import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
import com.said.B30.businessrules.helpers.paymenthelpers.PaymentMapper;
import com.said.B30.businessrules.helpers.paymenthelpers.PaymentUpdate;
import com.said.B30.dtos.paymentdtos.*;
import com.said.B30.infrastructure.entities.Order;
import com.said.B30.infrastructure.entities.Payment;
import com.said.B30.infrastructure.entities.Sell;
import com.said.B30.infrastructure.enums.PaymentStatus;
import com.said.B30.infrastructure.repositories.OrderRepository;
import com.said.B30.infrastructure.repositories.PaymentRepository;
import com.said.B30.infrastructure.repositories.SellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper mapper;
    private final OrderRepository orderRepository;
    private final SellRepository sellRepository;
    private final PaymentUpdate paymentUpdate;

    @Transactional
    public PaymentOrderResponseDto registerOrderPayment(PaymentOrderRequestDto paymentRequest){
        Order order = orderRepository.findById(paymentRequest.orderId())
                .orElseThrow(() -> new ResourceNotFoundException(paymentRequest.orderId()));
        Payment payment = mapper.toEntity(paymentRequest);

        order.addPayment(payment);
        updateOrderPaymentStatus(order);
        orderRepository.saveAndFlush(order);
        return mapper.toResponsePO(payment);
    }

    @Transactional
    public PaymentProductResponseDto registerSellPayment(PaymentProductRequestDto paymentRequest){
        Sell sell = sellRepository.findById(paymentRequest.sellId())
                .orElseThrow(() -> new ResourceNotFoundException(paymentRequest.sellId()));

        Payment payment = mapper.toEntity(paymentRequest);
        sell.addPayment(payment);
        updateSellPaymentStatus(sell);
        sellRepository.saveAndFlush(sell);
        return mapper.toResponsePP(payment);
    }

    private void updateOrderPaymentStatus(Order order){
        double totalPaid = order.getPayments().stream().mapToDouble(Payment::getAmount).sum();
        if(totalPaid >= order.getEstablishedValue()){
            order.setPaymentStatus(PaymentStatus.PAYMENT_OK);
        }
        else if(totalPaid < order.getEstablishedValue()){
            order.setPaymentStatus(PaymentStatus.PENDING_PAYMENT);
        }
    }

    private void updateSellPaymentStatus(Sell sell){
        double totalPaid = sell.getPayments().stream().mapToDouble(Payment::getAmount).sum();
        if(totalPaid >= sell.getTotalValue()){
            sell.setPaymentStatus(PaymentStatus.PAYMENT_OK);
        }
        else if(totalPaid < sell.getTotalValue()){
            sell.setPaymentStatus(PaymentStatus.PENDING_PAYMENT);
        }
    }

    public Set<PaymentOrderResponseDto> findPaymentsByOrderId(Long orderId){
        var order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(orderId));
        Set<Payment> payments = order.getPayments();
        return payments.stream().map(mapper::toResponsePO).collect(Collectors.toSet());
    }

    public Set<PaymentProductResponseDto> findPaymentsBySellId(Long sellId){
        var sell = sellRepository.findById(sellId).orElseThrow(() -> new ResourceNotFoundException(sellId));
        Set<Payment> payments = sell.getPayments();
        return payments.stream().map(mapper::toResponsePP).collect(Collectors.toSet());
    }

    @Transactional
    public PaymentOrderResponseDto updateOrderPaymentData(Long id, PaymentUpdateRequestDto paymentUpdateRequest){
        var payment = paymentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id));
        paymentUpdate.updatePaymentData(paymentUpdateRequest, payment);
        updateOrderPaymentStatus(payment.getOrder());
        return mapper.toResponsePO(paymentRepository.saveAndFlush(payment));
    }

    @Transactional
    public PaymentProductResponseDto updateSellPaymentData(Long id, PaymentUpdateRequestDto paymentUpdateRequest){
        var payment = paymentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id));
        paymentUpdate.updatePaymentData(paymentUpdateRequest, payment);
        updateSellPaymentStatus(payment.getSell());
        return mapper.toResponsePP(paymentRepository.saveAndFlush(payment));
    }

    @Transactional
    public void deletePayment(Long id){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        if(payment.getOrder() != null){
            Order order = payment.getOrder();
            order.getPayments().remove(payment);
            updateOrderPaymentStatus(order);
            orderRepository.saveAndFlush(order);
        } else if (payment.getSell() != null){
            Sell sell = payment.getSell();
            sell.getPayments().remove(payment);
            updateSellPaymentStatus(sell);
            sellRepository.saveAndFlush(sell);
        }
    }
}
