package com.said.B30.businessrules.services;

import com.said.B30.businessrules.exceptions.DataEntryException;
import com.said.B30.businessrules.exceptions.DeletionNotAllowedException;
import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
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
        if (productId == null || clientId == null || establishedValue == null){
            throw new DataEntryException("Certifique que o VALOR COBRADO, o CLIENTE (comprador) e o PRODUTO a ser vendido estão devidamente indicados na operaçaão");
        }else{
            var product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(productId));
            var client = clientRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException(clientId));
            if (product.getProductStatus() == ProductStatus.SOLD){
                throw new DataEntryException("Não é possível vender novamente um produto que já foi vendido");
            }
            else{
                product.setProductStatus(ProductStatus.SOLD);
                product.setEstablishedValue(establishedValue);
                product.setClient(client);
                product.setSaleDate(LocalDate.now());
                return mapper.toSoldResponse(productRepository.saveAndFlush(product));
            }
        }
    }

    public List<ProductFullResponseDto> findAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(mapper::toFullResponse).toList();
    }

    public ProductFullResponseDto findProductById(Long id){
        return mapper.toFullResponse(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    public ProductFullResponseDto updateData(Long id, ProductUpdateRequestDto requestDto){
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        productUpdate.updateProductData(requestDto, product);
        return mapper.toFullResponse(productRepository.saveAndFlush(product));
    }

    public void deleteProduct(Long id){
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        if (product.getProductStatus() == ProductStatus.SOLD){
            throw new DeletionNotAllowedException("Não é possível deletar um produto que já foi vendido");
        }
        else {
            productRepository.delete(product);
        }
    }
}
