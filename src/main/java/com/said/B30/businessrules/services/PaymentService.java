package com.said.B30.businessrules.services;

import com.said.B30.businessrules.exceptions.PaymentNotAllowedException;
import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
import com.said.B30.businessrules.helpers.paymenthelpers.PaymentMapper;
import com.said.B30.businessrules.helpers.paymenthelpers.PaymentUpdate;
import com.said.B30.dtos.paymentdtos.*;
import com.said.B30.infrastructure.entities.Order;
import com.said.B30.infrastructure.entities.Payment;
import com.said.B30.infrastructure.entities.Product;
import com.said.B30.infrastructure.enums.PaymentStatus;
import com.said.B30.infrastructure.repositories.OrderRepository;
import com.said.B30.infrastructure.repositories.PaymentRepository;
import com.said.B30.infrastructure.repositories.ProductRepository;
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
    private final ProductRepository productRepository;
    private final PaymentUpdate paymentUpdate;

    @Transactional
    public PaymentOrderResponseDto registerOrderPayment(PaymentOrderRequestDto paymentRequest){
        Order order = orderRepository.findById(paymentRequest.orderId())
                .orElseThrow(() -> new ResourceNotFoundException(paymentRequest.orderId()));
        Payment payment = mapper.toEntity(paymentRequest);

        order.addPayment(payment);
        updateOrderPaymentStatus(order);
        orderRepository.save(order);
        return mapper.toResponsePO(payment);
    }

    @Transactional
    public PaymentProductResponseDto registerProductPayment(PaymentProductRequestDto paymentRequest){
        Product product = productRepository.findById(paymentRequest.productId())
                .orElseThrow(() -> new ResourceNotFoundException(paymentRequest.productId()));

        if(product.getEstablishedValue() == null){
            throw new PaymentNotAllowedException("Não é possível lançar pagamento relativo a um produto que ainda não foi vendido");
        }

        Payment payment = mapper.toEntity(paymentRequest);
        product.addPayment(payment);
        updateProductPaymentStatus(product);
        productRepository.save(product);
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

    private void updateProductPaymentStatus(Product product){
        double totalPaid = product.getPayments().stream().mapToDouble(Payment::getAmount).sum();
        if(totalPaid >= product.getEstablishedValue()){
            product.setPaymentStatus(PaymentStatus.PAYMENT_OK);
        }
        else if(totalPaid < product.getEstablishedValue()){
            product.setPaymentStatus(PaymentStatus.PENDING_PAYMENT);
        }
    }

    public Set<PaymentOrderResponseDto> findPaymentsByOrderId(Long orderId){
        var order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(orderId));
        Set<Payment> payments = order.getPayments();
        return payments.stream().map(mapper::toResponsePO).collect(Collectors.toSet());
    }

    public Set<PaymentProductResponseDto> findPaymentsByProductId(Long productId){
        var product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(productId));
        Set<Payment> payments = product.getPayments();
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
    public PaymentProductResponseDto updateProductPaymentData(Long id, PaymentUpdateRequestDto paymentUpdateRequest){
        var payment = paymentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id));
        paymentUpdate.updatePaymentData(paymentUpdateRequest, payment);
        updateProductPaymentStatus(payment.getProduct());
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
            orderRepository.save(order);
        } else{
            Product product = payment.getProduct();
            product.getPayments().remove(payment);
            updateProductPaymentStatus(product);
            productRepository.save(product);
        }
    }
}
