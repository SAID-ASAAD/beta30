package com.said.B30.businessrules.services;

import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
import com.said.B30.businessrules.helpers.orderhelpers.OrderUpdate;
import com.said.B30.dtos.orderdtos.*;
import com.said.B30.infrastructure.entities.Order;
import com.said.B30.infrastructure.entities.Payment;
import com.said.B30.infrastructure.enums.OrderStatus;
import com.said.B30.infrastructure.enums.PaymentStatus;
import com.said.B30.infrastructure.repositories.ClientRepository;
import com.said.B30.infrastructure.repositories.OrderRepository;
import com.said.B30.businessrules.helpers.orderhelpers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final OrderMapper mapper;
    private final OrderUpdate orderUpdate;

    public OrderResponseDto createOrder(OrderRequestDto orderRequest){
        var client = clientRepository.findById(orderRequest.clientId())
                .orElseThrow(() -> new ResourceNotFoundException(orderRequest.clientId()));
        var order = mapper.toEntity(orderRequest);
        order.setClient(client);

        if(order.getDeposit() != null && order.getDeposit() > 0){
            Payment initialPayment = new Payment();
            initialPayment.setAmount(order.getDeposit());
            initialPayment.setPaymentDate(LocalDate.now());
            order.addPayment(initialPayment);
        }

        return mapper.toResponseDto(orderRepository.saveAndFlush(order));
    }

    public OrderResponseDto findOrderById(Long id){
        return mapper.toResponseDto(orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    public List<OrderResponseDto> findOrdersByClientId(Long clientId){
        return orderRepository.findByClientId(clientId)
                .stream().map(mapper::toResponseDto).collect(Collectors.toList());
    }

    public List<OrderResponseDto> findOrdersByDescription(String description){
       return orderRepository.findByDescriptionContainingIgnoreCase(description)
                .stream().map(mapper::toResponseDto).collect(Collectors.toList());
    }

    public Page<OrderResponseDto> findAllOrdersPaginated(Pageable pageable){
        return orderRepository.findAllOrdersSortedByUrgency(pageable).map(mapper::toResponseDto);
    }

    public List<OrderResponseDto> findAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(mapper::toResponseDto).toList();
    }

    @Transactional
    public void updatePaymentStatusForOverdueOrders() {
        List<Order> overdueOrders = orderRepository.findOverdueOrders(LocalDate.now(), PaymentStatus.DEPOSIT_PAID);
        for (Order order : overdueOrders) {
            order.setPaymentStatus(PaymentStatus.PENDING_PAYMENT);
        }
        orderRepository.saveAll(overdueOrders);
    }

    public OrderUpdateResponseDto updateOrderData(Long id, OrderUpdateRequestDto orderUpdateRequest){
        if (!orderRepository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }else {
                var order = orderRepository.getReferenceById(id);
                orderUpdate.updateOrderData(orderUpdateRequest, order);
                return mapper.toUpdateResponseDto(orderRepository.saveAndFlush(order));


        }
    }

    public void cancelOrder(Long id){
        if (!orderRepository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }else {
            var order = orderRepository.getReferenceById(id);
            order.setOrderStatus(OrderStatus.CANCELED);
            orderRepository.saveAndFlush(order);
        }
    }
}
