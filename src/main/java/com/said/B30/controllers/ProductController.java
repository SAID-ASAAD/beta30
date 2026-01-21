package com.said.B30.controllers;

import com.said.B30.businessrules.services.ProductService;
import com.said.B30.dtos.productdtos.*;
import com.said.B30.infrastructure.entities.Sell;
import com.said.B30.infrastructure.enums.ProductStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ModelAndView getProductsPage(@RequestParam(defaultValue = "0") int page){
        Pageable pageable = PageRequest.of(page, 8, Sort.by("id").descending());
        Page<ProductFullResponseDto> productPage = productService.findAllProductsPaginated(pageable);

        ModelAndView mv = new ModelAndView("products/products-page");
        mv.addObject("products", productPage);
        return mv;
    }

    @GetMapping("/search")
    @ResponseBody
    public List<ProductFullResponseDto> searchProducts(@RequestParam String description) {
        return productService.findProductsByDescription(description);
    }

    @GetMapping("/register")
    public ModelAndView getRegisterProductPage() {
        ModelAndView mv = new ModelAndView("products/product-register-form");
        mv.addObject("productRequestDto", new ProductRequestDto(null, null, null, null, null, null));
        return mv;
    }

    @PostMapping("/register")
    public ModelAndView registerProduct(@Valid ProductRequestDto productRequest, BindingResult result){
        if (result.hasErrors()) {
            return new ModelAndView("products/product-register-form");
        }
        productService.registerProduct(productRequest);
        return new ModelAndView("redirect:/products");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditProductForm(@PathVariable Long id) {
        ProductUpdateRequestDto updateDto = productService.getProductForUpdate(id);
        ProductFullResponseDto product = productService.findProductById(id);
        
        ModelAndView mv = new ModelAndView("products/product-update-form");
        mv.addObject("product", product);
        mv.addObject("productUpdateRequestDto", updateDto);
        mv.addObject("productStatuses", ProductStatus.values());
        
        return mv;
    }

    @PutMapping("/edit/{id}")
    public ModelAndView updateData(@PathVariable Long id, @Valid ProductUpdateRequestDto requestDto, BindingResult result){
        if (result.hasErrors()) {
            ModelAndView mv = new ModelAndView("products/product-update-form");
            mv.addObject("product", productService.findProductById(id)); 
            mv.addObject("productStatuses", ProductStatus.values());
            return mv;
        }
        productService.updateData(id, requestDto);
        return new ModelAndView("redirect:/products/details/" + id);
    }

    @PutMapping("/sell/{id}")
    public ModelAndView sellProduct(
            @PathVariable Long id, 
            @Valid ProductSaleDto saleDto, 
            BindingResult result){
        
        if (result.hasErrors()) {
             ProductFullResponseDto product = productService.findProductById(id);
             ModelAndView mv = new ModelAndView("products/product-details");
             mv.addObject("product", product);
             mv.addObject("productSaleDto", saleDto);
             mv.addObject("errorMessage", "Dados inválidos para a venda.");
             return mv;
        }

        productService.sellProduct(id, saleDto.clientId(), saleDto.quantity(), saleDto.establishedValue(), saleDto.initialPayment());
        return new ModelAndView("redirect:/products/details/" + id);
    }

    @GetMapping("/details/{id}")
    public ModelAndView getProductDetailsPage(@PathVariable Long id) {
        ProductFullResponseDto product = productService.findProductById(id);
        List<Sell> sales = productService.findSalesByProductId(id);
        
        ModelAndView mv = new ModelAndView("products/product-details");
        mv.addObject("product", product);
        mv.addObject("sales", sales);
        
        Double totalSoldValue = sales.stream().mapToDouble(Sell::getTotalValue).sum();
        Integer totalSoldQuantity = sales.stream().mapToInt(Sell::getQuantity).sum();
        mv.addObject("totalSoldValue", totalSoldValue);
        mv.addObject("totalSoldQuantity", totalSoldQuantity);

        Double stockValue = product.quantity() * product.preEstablishedValue();
        mv.addObject("stockValue", stockValue);
        
        mv.addObject("productSaleDto", new ProductSaleDto(null, null, null, null, null));

        return mv;
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ModelAndView("redirect:/products");
    }

    // Métodos API
    @GetMapping("/findAllProducts")
    @ResponseBody
    public ResponseEntity<List<ProductFullResponseDto>> findAllProducts(){
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<ProductFullResponseDto> findProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findProductById(id));
    }
}
