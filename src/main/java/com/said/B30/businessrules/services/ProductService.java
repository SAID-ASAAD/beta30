package com.said.B30.businessrules.services;

import com.said.B30.businessrules.helpers.producthelpers.ProductMapper;
import com.said.B30.businessrules.helpers.producthelpers.ProductUpdate;
import com.said.B30.dtos.productdtos.*;
import com.said.B30.infrastructure.entities.Product;
import com.said.B30.infrastructure.enums.ProductStatus;
import com.said.B30.infrastructure.repositories.ClientRepository;
import com.said.B30.infrastructure.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;
    private final ClientRepository clientRepository;
    private final ProductUpdate productUpdate;

    public ProductResponseDto registerProduct(ProductRequestDto productRequest){
        return mapper.toResponse(productRepository.saveAndFlush(mapper.toEntity(productRequest)));
    }

    public ProductSoldResponseDto sellProduct(Long productId, Long clientId, Double establishedValue){
        var product = productRepository.getReferenceById(productId);
        var client = clientRepository.getReferenceById(clientId);
        product.setProductStatus(ProductStatus.SOLD);
        product.setEstablishedValue(establishedValue);
        product.setClient(client);
        product.setSaleDate(LocalDate.now());
        return mapper.toSoldResponse(productRepository.saveAndFlush(product));
    }

    public List<ProductFullResponseDto> findAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(mapper::toFullResponse).toList();
    }

    public ProductFullResponseDto findProductById(Long id){
        return mapper.toFullResponse(productRepository.findById(id).orElseThrow());
    }

    public ProductFullResponseDto updateData(Long id, ProductUpdateRequestDto requestDto){
        var product = productRepository.getReferenceById(id);
        productUpdate.updateProductData(requestDto, product);
        return mapper.toFullResponse(productRepository.saveAndFlush(product));
    }

    public void deleteProduct(Long id){
        //validar se o produto já não está vendido e ver como lidar com essa troca de status e dados financeiros
        var product = productRepository.getReferenceById(id);
        productRepository.delete(product);
    }
}
