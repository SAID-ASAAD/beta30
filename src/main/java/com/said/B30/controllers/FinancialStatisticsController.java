package com.said.B30.controllers;

import com.said.B30.businessrules.services.FinancialStatisticsService;
import com.said.B30.dtos.expensedtos.ExpenseResponseDto;
import com.said.B30.dtos.paymentdtos.PaymentResponseDto;
import com.said.B30.dtos.paymentdtos.ProfitDetailDto;
import com.said.B30.dtos.paymentdtos.ReceivableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/finances/statistics")
@RequiredArgsConstructor
public class FinancialStatisticsController {

    private final FinancialStatisticsService financialStatisticsService;

    @GetMapping
    public ModelAndView getStatisticsPage() {
        ModelAndView mv = new ModelAndView("finances/statistics-page");
        
        mv.addObject("totalRevenue", financialStatisticsService.getTotalRevenue());
        mv.addObject("totalReceivable", financialStatisticsService.getTotalReceivable());
        mv.addObject("totalProfit", financialStatisticsService.getTotalNetProfit());
        mv.addObject("totalGrossProfit", financialStatisticsService.getTotalGrossProfit());
        mv.addObject("totalExpenses", financialStatisticsService.getTotalExpenses());
        
        return mv;
    }

    @GetMapping("/payments/all")
    @ResponseBody
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments(){
        return ResponseEntity.ok(financialStatisticsService.getAllPayments());
    }

    @GetMapping("/revenue/total")
    @ResponseBody
    public ResponseEntity<Double> getTotalRevenue(){
        return ResponseEntity.ok(financialStatisticsService.getTotalRevenue());
    }

    @GetMapping("/payments/date-range")
    @ResponseBody
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate){
        return ResponseEntity.ok(financialStatisticsService.getPaymentsByDateRange(startDate, endDate));
    }

    @GetMapping("/revenue/date-range")
    @ResponseBody
    public ResponseEntity<Double> getRevenueByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate){
        return ResponseEntity.ok(financialStatisticsService.getRevenueByDateRange(startDate, endDate));
    }

    @GetMapping("/payments/client")
    @ResponseBody
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByClientId(@RequestParam Long clientId){
        return ResponseEntity.ok(financialStatisticsService.getPaymentsByClientId(clientId));
    }

    @GetMapping("/revenue/client")
    @ResponseBody
    public ResponseEntity<Double> getRevenueByClientId(@RequestParam Long clientId){
        return ResponseEntity.ok(financialStatisticsService.getRevenueByClientId(clientId));
    }

    @GetMapping("/receivable/total")
    @ResponseBody
    public ResponseEntity<Double> getTotalReceivable(){
        return ResponseEntity.ok(financialStatisticsService.getTotalReceivable());
    }

    @GetMapping("/receivable/date-range")
    @ResponseBody
    public ResponseEntity<Double> getReceivableByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate){
        return ResponseEntity.ok(financialStatisticsService.getReceivableByDateRange(startDate, endDate));
    }
    
    @GetMapping("/receivable/list/date-range")
    @ResponseBody
    public ResponseEntity<List<ReceivableDto>> getReceivablesListByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate){
        return ResponseEntity.ok(financialStatisticsService.getReceivablesListByDateRange(startDate, endDate));
    }

    @GetMapping("/receivable/client")
    @ResponseBody
    public ResponseEntity<Double> getReceivableByClientId(@RequestParam Long clientId){
        return ResponseEntity.ok(financialStatisticsService.getReceivableByClientId(clientId));
    }

    @GetMapping("/receivable/order")
    @ResponseBody
    public ResponseEntity<Double> getReceivableByOrderId(@RequestParam Long orderId){
        return ResponseEntity.ok(financialStatisticsService.getReceivableByOrderId(orderId));
    }

    @GetMapping("/receivable/sell")
    @ResponseBody
    public ResponseEntity<Double> getReceivableBySellId(@RequestParam Long sellId){
        return ResponseEntity.ok(financialStatisticsService.getReceivableBySellId(sellId));
    }

    @GetMapping("/profit/order")
    @ResponseBody
    public ResponseEntity<Double> getProfitByOrderId(@RequestParam Long orderId){
        return ResponseEntity.ok(financialStatisticsService.getProfitByOrderId(orderId));
    }

    @GetMapping("/profit/product")
    @ResponseBody
    public ResponseEntity<Double> getProfitByProductId(@RequestParam Long productId){
        return ResponseEntity.ok(financialStatisticsService.getProfitByProductId(productId));
    }

    @GetMapping("/profit/total-orders")
    @ResponseBody
    public ResponseEntity<Double> getTotalOrdersProfit(){
        return ResponseEntity.ok(financialStatisticsService.getTotalOrdersProfit());
    }

    @GetMapping("/profit/total-products")
    @ResponseBody
    public ResponseEntity<Double> getTotalProductsProfit(){
        return ResponseEntity.ok(financialStatisticsService.getTotalProductsProfit());
    }

    @GetMapping("/profit/total")
    @ResponseBody
    public ResponseEntity<Double> getTotalProfitApi(){
        return ResponseEntity.ok(financialStatisticsService.getTotalNetProfit());
    }

    @GetMapping("/profit/date-range")
    @ResponseBody
    public ResponseEntity<Double> getTotalProfitByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate){
        return ResponseEntity.ok(financialStatisticsService.getTotalNetProfitByDateRange(startDate, endDate));
    }
    
    @GetMapping("/profit/gross/total")
    @ResponseBody
    public ResponseEntity<Double> getTotalGrossProfitApi(){
        return ResponseEntity.ok(financialStatisticsService.getTotalGrossProfit());
    }

    @GetMapping("/profit/gross/date-range")
    @ResponseBody
    public ResponseEntity<Double> getTotalGrossProfitByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate){
        return ResponseEntity.ok(financialStatisticsService.getTotalGrossProfitByDateRange(startDate, endDate));
    }
    
    @GetMapping("/profit/details/date-range")
    @ResponseBody
    public ResponseEntity<List<ProfitDetailDto>> getProfitDetailsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate){
        return ResponseEntity.ok(financialStatisticsService.getProfitDetailsByDateRange(startDate, endDate));
    }
    
    @GetMapping("/expenses/total")
    @ResponseBody
    public ResponseEntity<Double> getTotalExpenses(){
        return ResponseEntity.ok(financialStatisticsService.getTotalExpenses());
    }
    
    @GetMapping("/expenses/date-range")
    @ResponseBody
    public ResponseEntity<Double> getTotalExpensesByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate){
        return ResponseEntity.ok(financialStatisticsService.getTotalExpensesByDateRange(startDate, endDate));
    }
    
    @GetMapping("/expenses/list/date-range")
    @ResponseBody
    public ResponseEntity<List<ExpenseResponseDto>> getExpensesListByDateRange(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate){
        return ResponseEntity.ok(financialStatisticsService.getExpensesByDateRange(startDate, endDate));
    }
}
