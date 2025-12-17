package com.said.B30.businessrules.services;

import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
import com.said.B30.businessrules.helpers.paymenthelpers.PaymentMapper;
import com.said.B30.businessrules.helpers.paymenthelpers.PaymentUpdate;
import com.said.B30.dtos.paymentdtos.PaymentRequestDto;
import com.said.B30.dtos.paymentdtos.PaymentResponseDto;
import com.said.B30.dtos.paymentdtos.PaymentUpdateRequestDto;
import com.said.B30.infrastructure.entities.Order;
import com.said.B30.infrastructure.entities.Payment;
import com.said.B30.infrastructure.enums.PaymentStatus;
import com.said.B30.infrastructure.repositories.OrderRepository;
import com.said.B30.infrastructure.repositories.PaymentRepository;
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
    private final PaymentUpdate paymentUpdate;

    @Transactional
    public PaymentResponseDto registerPayment(PaymentRequestDto paymentRequest){
        Order order = orderRepository.findById(paymentRequest.orderId())
                .orElseThrow(() -> new ResourceNotFoundException(paymentRequest.orderId()));
        Payment payment = mapper.toEntity(paymentRequest);

        order.addPayment(payment);
        updatePaymentStatus(order);
        orderRepository.save(order);
        return mapper.toResponse(payment);
    }

    private void updatePaymentStatus(Order order){
        double totalPaid = order.getPayments().stream().mapToDouble(Payment::getAmount).sum();
        if(totalPaid >= order.getEstablishedValue()){
            order.setPaymentStatus(PaymentStatus.PAYMENT_OK);
        }
        else if(totalPaid < order.getEstablishedValue()){
            order.setPaymentStatus(PaymentStatus.PENDING_PAYMENT);
        }
    }

    public Set<PaymentResponseDto> findPaymentsByOrderId(Long orderId){
        var order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(orderId));
        Set<Payment> payments = order.getPayments();
        return payments.stream().map(mapper::toResponse).collect(Collectors.toSet());
    }

    public PaymentResponseDto updatePaymentData(Long id, PaymentUpdateRequestDto paymentUpdateRequest){
        var payment = paymentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id));
        paymentUpdate.updatePaymentData(paymentUpdateRequest, payment);
        updatePaymentStatus(payment.getOrder());
        return mapper.toResponse(paymentRepository.saveAndFlush(payment));
    }

    @Transactional
    public void deletePayment(Long id){
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));

        Order order = payment.getOrder();
        order.getPayments().remove(payment);
        updatePaymentStatus(order);
        orderRepository.save(order);
    }
}
