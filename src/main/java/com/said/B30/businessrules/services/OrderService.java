package com.said.B30.businessrules.services;

import com.said.B30.businessrules.exceptions.DataEntryException;
import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
import com.said.B30.businessrules.helpers.orderhelpers.OrderUpdate;
import com.said.B30.dtos.orderdtos.*;
import com.said.B30.infrastructure.entities.Client;
import com.said.B30.infrastructure.entities.Order;
import com.said.B30.infrastructure.entities.Payment;
import com.said.B30.infrastructure.enums.OrderStatus;
import com.said.B30.infrastructure.repositories.ClientRepository;
import com.said.B30.infrastructure.repositories.OrderRepository;
import com.said.B30.businessrules.helpers.orderhelpers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final OrderMapper mapper;
    private final OrderUpdate orderUpdate;

    public OrderResponseDto createOrder(OrderRequestDto orderRequest){
        Client client = clientRepository.findById(orderRequest.clientId())
                .orElseThrow(() -> new ResourceNotFoundException(orderRequest.clientId()));
        Order order = mapper.toEntity(orderRequest);
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

    public List<OrderResponseDto> findAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(mapper::toResponseDto).toList();
    }

    public OrderUpdateResponseDto updateOrderData(Long id, OrderUpdateRequestDto orderUpdateRequest){
        if (!orderRepository.existsById(id)){
            throw new ResourceNotFoundException(id);
        }else {
            try {
                var order = orderRepository.getReferenceById(id);
                orderUpdate.updateOrderData(orderUpdateRequest, order);
                return mapper.toUpdateResponseDto(orderRepository.saveAndFlush(order));
            } catch (DataIntegrityViolationException e){
                throw new DataEntryException("Certifique que a NOTA FISCAL cadastrada não esteja já cadastrada em outro pedido");
            }

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
