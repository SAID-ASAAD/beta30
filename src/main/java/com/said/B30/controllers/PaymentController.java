package com.said.B30.controllers;

import com.said.B30.businessrules.services.PaymentService;
import com.said.B30.dtos.paymentdtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/orders")
    public ModelAndView registerOrderPayment(@Valid PaymentOrderRequestDto paymentRequest){
        paymentService.registerOrderPayment(paymentRequest);
        return new ModelAndView("redirect:/orders/details/" + paymentRequest.orderId());
    }

    @PostMapping("/sells")
    public ModelAndView registerSellPayment(@Valid PaymentProductRequestDto paymentRequest){
        paymentService.registerSellPayment(paymentRequest);
        return new ModelAndView("redirect:/products"); 
    }

    @GetMapping("/orders/{orderId}")
    @ResponseBody
    public ResponseEntity<Set<PaymentOrderResponseDto>> findPaymentsByOrderId(@PathVariable Long orderId){
        return ResponseEntity.ok(paymentService.findPaymentsByOrderId(orderId));
    }

    @GetMapping("/sells/{sellId}")
    @ResponseBody
    public ResponseEntity<Set<PaymentProductResponseDto>> findPaymentsBySellId(@PathVariable Long sellId){
        return ResponseEntity.ok(paymentService.findPaymentsBySellId(sellId));
    }

    @PutMapping("/orders/{id}")
    @ResponseBody
    public ResponseEntity<PaymentOrderResponseDto> updateOrderPaymentData(@PathVariable Long id, @Valid @RequestBody PaymentUpdateRequestDto paymentUpdateRequest){
        return ResponseEntity.ok(paymentService.updateOrderPaymentData(id, paymentUpdateRequest));
    }

    @PutMapping("/sells/{id}")
    @ResponseBody
    public ResponseEntity<PaymentProductResponseDto> updateSellPaymentData(@PathVariable Long id, @Valid @RequestBody PaymentUpdateRequestDto paymentUpdateRequest){
        return ResponseEntity.ok(paymentService.updateSellPaymentData(id, paymentUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ModelAndView deletePaymentById(@PathVariable Long id, @RequestParam(required = false) String redirectUrl){
        paymentService.deletePayment(id);
        return new ModelAndView("redirect:" + (redirectUrl != null ? redirectUrl : "/home"));
    }
}
