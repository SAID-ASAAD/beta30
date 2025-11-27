package com.said.B30.businessrules.services;

import com.said.B30.dtos.orderdtos.*;
import com.said.B30.infrastructure.entities.Client;
import com.said.B30.infrastructure.entities.Order;
import com.said.B30.infrastructure.enums.OrderStatus;
import com.said.B30.infrastructure.repositories.ClientRepository;
import com.said.B30.infrastructure.repositories.OrderRepository;
import com.said.B30.businessrules.helpers.orderHelpers.OrderMapper;
import com.said.B30.businessrules.helpers.orderHelpers.OrderUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final OrderMapper mapper;
    private final OrderUpdate orderUpdate;

    public OrderResponseDto createOrder(OrderRequestDto orderRequest){

        Client client = clientRepository.findById(orderRequest.clientId()).orElseThrow();
        Order order = mapper.toEntity(orderRequest);
        order.setClient(client);
        return mapper.toResponseDto(orderRepository.save(order));
    }

    public OrderResponseDto findOrderById(Long id){
        return mapper.toResponseDto(orderRepository.findById(id).orElseThrow());
    }

    public List<OrderResponseDto> findAllOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(mapper::toResponseDto).toList();
    }

    public OrderResponseDto updateOrderData(Long id, OrderUpdateRequestDto orderUpdateRequest){
        var order = orderRepository.getReferenceById(id);
        orderUpdate.updateOrderData(orderUpdateRequest, order);
        return mapper.toResponseDto(orderRepository.saveAndFlush(order));
    }

    public void cancelOrder(Long id){
        var order = orderRepository.getReferenceById(id);
        order.setOrderStatus(OrderStatus.CANCELED);
        orderRepository.saveAndFlush(order);
    }
}
