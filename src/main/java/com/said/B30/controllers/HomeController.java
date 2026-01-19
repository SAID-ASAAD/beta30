package com.said.B30.controllers;

import com.said.B30.businessrules.services.OrderService;
import com.said.B30.dtos.orderdtos.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final OrderService orderService;

    @GetMapping(value = {"/", "/home"})
    public ModelAndView home() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<OrderResponseDto> orderPage = orderService.findOrdersNotCanceled(pageable);
        
        ModelAndView mv = new ModelAndView("home");
        mv.addObject("orders", orderPage);
        return mv;
    }
}
