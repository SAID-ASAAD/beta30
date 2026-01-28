package com.said.B30.controllers;

import com.said.B30.businessrules.services.SellService;
import com.said.B30.dtos.selldtos.SellUpdateRequestDto;
import com.said.B30.infrastructure.entities.Sell;
import com.said.B30.infrastructure.enums.PaymentStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/finances/sells")
@RequiredArgsConstructor
public class SellController {

    private final SellService sellService;

    @GetMapping("/edit/{id}")
    public ModelAndView getEditSellForm(@PathVariable Long id) {
        SellUpdateRequestDto updateDto = sellService.getSellForUpdate(id);
        Sell sell = sellService.findSellById(id);
        
        ModelAndView mv = new ModelAndView("finance/sell-update-form");
        mv.addObject("sell", sell);
        mv.addObject("sellUpdateRequestDto", updateDto);
        mv.addObject("paymentStatuses", PaymentStatus.values());
        return mv;
    }

    @PutMapping("/edit/{id}")
    public ModelAndView updateSell(@PathVariable Long id, @Valid SellUpdateRequestDto requestDto, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("finance/sell-update-form");
            mv.addObject("sell", sellService.findSellById(id));
            mv.addObject("paymentStatuses", PaymentStatus.values());
            return mv;
        }
        sellService.updateSellData(id, requestDto);
        Sell sell = sellService.findSellById(id);
        return new ModelAndView("redirect:/products/details/" + sell.getProduct().getId());
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteSell(@PathVariable Long id) {
        Sell sell = sellService.findSellById(id);
        Long productId = sell.getProduct().getId();
        sellService.deleteSell(id);
        return new ModelAndView("redirect:/products/details/" + productId);
    }
}
