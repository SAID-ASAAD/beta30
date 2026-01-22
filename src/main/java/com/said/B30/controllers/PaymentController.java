package com.said.B30.controllers;

import com.said.B30.businessrules.services.PaymentService;
import com.said.B30.dtos.paymentdtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
@RequestMapping("/finances/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/register")
    public ModelAndView getRegisterPaymentPage() {
        ModelAndView mv = new ModelAndView("finances/payment-register-form");
        mv.addObject("paymentOrderRequestDto", new PaymentOrderRequestDto(null, null, null));
        mv.addObject("paymentProductRequestDto", new PaymentProductRequestDto(null, null, null));
        return mv;
    }

    @PostMapping("/orders")
    public ModelAndView registerOrderPayment(@Valid PaymentOrderRequestDto paymentRequest){
        paymentService.registerOrderPayment(paymentRequest);
        return new ModelAndView("redirect:/finances/payments");
    }

    @PostMapping("/sells")
    public ModelAndView registerSellPayment(@Valid PaymentProductRequestDto paymentRequest){
        paymentService.registerSellPayment(paymentRequest);
        return new ModelAndView("redirect:/finances/payments");
    }
    
    @GetMapping("/edit/{id}")
    public ModelAndView getEditPaymentForm(@PathVariable Long id) {
        PaymentResponseDto payment = paymentService.findPaymentById(id);
        PaymentUpdateRequestDto updateDto = paymentService.getPaymentForUpdate(id);
        
        ModelAndView mv = new ModelAndView("finances/payment-update-form");
        mv.addObject("payment", payment);
        mv.addObject("paymentUpdateRequestDto", updateDto);
        return mv;
    }
    
    @PutMapping("/edit/{id}")
    public ModelAndView updatePayment(@PathVariable Long id, @Valid PaymentUpdateRequestDto updateDto, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("finances/payment-update-form");
            mv.addObject("payment", paymentService.findPaymentById(id));
            return mv;
        }
        paymentService.updatePaymentData(id, updateDto);
        return new ModelAndView("redirect:/finances/payments");
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

    @PutMapping("/api/orders/{id}")
    @ResponseBody
    public ResponseEntity<PaymentOrderResponseDto> updateOrderPaymentData(@PathVariable Long id, @Valid @RequestBody PaymentUpdateRequestDto paymentUpdateRequest){
        return ResponseEntity.ok(paymentService.updateOrderPaymentData(id, paymentUpdateRequest));
    }

    @PutMapping("/api/sells/{id}")
    @ResponseBody
    public ResponseEntity<PaymentProductResponseDto> updateSellPaymentData(@PathVariable Long id, @Valid @RequestBody PaymentUpdateRequestDto paymentUpdateRequest){
        return ResponseEntity.ok(paymentService.updateSellPaymentData(id, paymentUpdateRequest));
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deletePaymentById(@PathVariable Long id, @RequestParam(required = false) String redirectUrl){
        paymentService.deletePayment(id);
        return new ModelAndView("redirect:" + (redirectUrl != null ? redirectUrl : "/finances/payments"));
    }
}
