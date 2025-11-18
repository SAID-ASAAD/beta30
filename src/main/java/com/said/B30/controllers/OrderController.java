package com.said.B30.controllers;

import com.said.B30.dtos.orderDtos.OrderRequestDto;
import com.said.B30.dtos.orderDtos.OrderResponseDto;
import com.said.B30.dtos.orderDtos.OrderUpdateRequestDto;
import com.said.B30.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createClient(@RequestBody OrderRequestDto orderRequest){
        var obj = orderService.createOrder(orderRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.id()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> findOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> findAllOrders(){
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDto> updateOrderData(@PathVariable Long id, @RequestBody OrderUpdateRequestDto orderUpdateRequest){
        return ResponseEntity.ok(orderService.updateOrderData(id, orderUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long id){
        orderService.cancelOrder(id);
        return ResponseEntity.accepted().build();
    }
}
