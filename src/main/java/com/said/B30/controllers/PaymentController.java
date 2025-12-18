package com.said.B30.controllers;

import com.said.B30.businessrules.services.PaymentService;
import com.said.B30.dtos.paymentdtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@RequestMapping("/pagamentos")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pedidos")
    public ResponseEntity<PaymentOrderResponseDto> registerPayment(@Valid @RequestBody PaymentOrderRequestDto paymentRequest){
        var obj = paymentService.registerOrderPayment(paymentRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.id()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PostMapping("/produtos")
    public ResponseEntity<PaymentProductResponseDto> registerPayment(@Valid @RequestBody PaymentProductRequestDto paymentRequest){
        var obj = paymentService.registerProductPayment(paymentRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.id()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @GetMapping("/pedidos/{orderId}")
    public ResponseEntity<Set<PaymentOrderResponseDto>> findPaymentsByOrderId(@PathVariable Long orderId){
        return ResponseEntity.ok(paymentService.findPaymentsByOrderId(orderId));
    }

    @GetMapping("/produtos/{orderId}")
    public ResponseEntity<Set<PaymentProductResponseDto>> findPaymentsByProductId(@PathVariable Long orderId){
        return ResponseEntity.ok(paymentService.findPaymentsByProductId((orderId)));
    }

    @PutMapping("/pedidos/{id}")
    public ResponseEntity<PaymentOrderResponseDto> updateOrderPaymentData(@PathVariable Long id, @Valid @RequestBody PaymentUpdateRequestDto paymentUpdateRequest){
        return ResponseEntity.ok(paymentService.updateOrderPaymentData(id, paymentUpdateRequest));
    }

    @PutMapping("/produtos/{id}")
    public ResponseEntity<PaymentProductResponseDto> updateProductPaymentData(@PathVariable Long id, @Valid @RequestBody PaymentUpdateRequestDto paymentUpdateRequest){
        return ResponseEntity.ok(paymentService.updateProductPaymentData(id, paymentUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentById(@PathVariable Long id){
        paymentService.deletePayment(id);
        return ResponseEntity.accepted().build();
    }
}
