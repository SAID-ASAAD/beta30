package com.said.B30.controllers;

import com.said.B30.businessrules.services.ProductService;
import com.said.B30.dtos.productdtos.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> registerProduct(@Valid @RequestBody ProductRequestDto productRequest){
        var obj = productService.registerProduct(productRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.id()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping("/venda/{productId}")
    public ResponseEntity<ProductSoldResponseDto> sellProduct(
            @PathVariable Long productId, @RequestParam Long clientId, Double establishedValue, Double initialPayment){
        return ResponseEntity.ok(productService.sellProduct(productId, clientId, establishedValue, initialPayment));
    }

    @GetMapping
    public ResponseEntity<List<ProductFullResponseDto>> findAllProducts(){
        return ResponseEntity.ok(productService.findAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductFullResponseDto> findProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductFullResponseDto> updateData(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequestDto requestDto){
        return ResponseEntity.ok(productService.updateData(id, requestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.accepted().build();
    }
}
