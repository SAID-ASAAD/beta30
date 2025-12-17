package com.said.B30.controllers;

import com.said.B30.businessrules.services.PaymentService;
import com.said.B30.dtos.paymentdtos.PaymentRequestDto;
import com.said.B30.dtos.paymentdtos.PaymentResponseDto;
import com.said.B30.dtos.paymentdtos.PaymentUpdateRequestDto;
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

    @PostMapping
    public ResponseEntity<PaymentResponseDto> registerPayment(@Valid @RequestBody PaymentRequestDto paymentRequest){
        var obj = paymentService.registerPayment(paymentRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.id()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Set<PaymentResponseDto>> findPaymentsByOrderId(@PathVariable Long orderId){
        return ResponseEntity.ok(paymentService.findPaymentsByOrderId(orderId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> updatePaymentData(@PathVariable Long id, @Valid @RequestBody PaymentUpdateRequestDto paymentUpdateRequest){
        return ResponseEntity.ok(paymentService.updatePaymentData(id, paymentUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentById(@PathVariable Long id){
        paymentService.deletePayment(id);
        return ResponseEntity.accepted().build();
    }
}
