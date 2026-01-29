package com.said.B30.controllers;

import com.said.B30.dtos.orderdtos.OrderRequestDto;
import com.said.B30.dtos.orderdtos.OrderResponseDto;
import com.said.B30.dtos.orderdtos.OrderUpdateRequestDto;
import com.said.B30.businessrules.services.OrderService;
import com.said.B30.businessrules.services.ClientService;
import com.said.B30.dtos.clientdtos.ClientResponseDto;
import com.said.B30.infrastructure.enums.Category;
import com.said.B30.infrastructure.enums.OrderStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ClientService clientService;

    @GetMapping
    public ModelAndView getOrdersPage(@RequestParam(defaultValue = "0") int page){
        Pageable pageable = PageRequest.of(page, 8);
        Page<OrderResponseDto> orderPage = orderService.findAllOrdersPaginated(pageable);

        ModelAndView mv = new ModelAndView("orders/orders-page");
        mv.addObject("orders", orderPage);
        return mv;
    }

    @GetMapping("/search")
    @ResponseBody
    public List<OrderResponseDto> searchOrders(@RequestParam String description) {
        return orderService.findOrdersByDescription(description);
    }

    @GetMapping("/register")
    public ModelAndView getRegisterOrderPage() {
        ModelAndView mv = new ModelAndView("orders/order-register-form");
        // Inicializa deposit com 0.0 para evitar campo vazio/bugado
        mv.addObject("orderRequestDto", new OrderRequestDto(null, null, null, null, 0.0, null));
        mv.addObject("categories", Category.values());
        return mv;
    }

    @PostMapping("/register")
    public ModelAndView createOrder(@Valid OrderRequestDto orderRequest, BindingResult result){
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("orders/order-register-form");
            mv.addObject("categories", Category.values());
            return mv;
        }
        orderService.createOrder(orderRequest);
        return new ModelAndView("redirect:/orders");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditOrderForm(@PathVariable Long id) {
        // Usa o método do service para obter o DTO pronto para edição
        OrderUpdateRequestDto updateDto = orderService.getOrderForUpdate(id);
        OrderResponseDto order = orderService.findOrderById(id); 
        
        ModelAndView mv = new ModelAndView("orders/order-update-form");
        mv.addObject("order", order);
        mv.addObject("orderUpdateRequestDto", updateDto);
        mv.addObject("categories", Category.values());
        mv.addObject("statuses", OrderStatus.values());
        
        return mv;
    }

    @PutMapping("/edit/{id}")
    public ModelAndView updateOrderData(@PathVariable Long id, @Valid OrderUpdateRequestDto orderUpdate, BindingResult result) {
        if (result.hasErrors()) {
             ModelAndView mv = new ModelAndView("orders/order-update-form");
             // Reconstrução manual do OrderResponseDto com 15 argumentos
             mv.addObject("order", new OrderResponseDto(id, null, null, null, null, null, null, null, null, null, null, null, null, null, null));
             mv.addObject("categories", Category.values());
             mv.addObject("statuses", OrderStatus.values());
             return mv;
        }
        orderService.updateOrderData(id, orderUpdate);
        return new ModelAndView("redirect:/orders/details/" + id);
    }

    @GetMapping("/details/{id}")
    public ModelAndView getOrderDetailsPage(@PathVariable Long id) {
        OrderResponseDto order = orderService.findOrderById(id);
        ClientResponseDto client = clientService.findClientById(order.clientId());
        
        ModelAndView mv = new ModelAndView("orders/order-details");
        mv.addObject("order", order);
        mv.addObject("client", client);
        return mv;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> findOrderById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @GetMapping("/findAllOrders")
    public ResponseEntity<List<OrderResponseDto>> findAllOrders(){
        return ResponseEntity.ok(orderService.findAllOrders());
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView cancelOrder(@PathVariable Long id){
        orderService.cancelOrder(id);
        return new ModelAndView("redirect:/orders");
    }
}
