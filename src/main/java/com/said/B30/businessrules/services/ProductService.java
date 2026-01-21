package com.said.B30.businessrules.services;

import com.said.B30.businessrules.exceptions.DataEntryException;
import com.said.B30.businessrules.exceptions.DeletionNotAllowedException;
import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
import com.said.B30.businessrules.helpers.producthelpers.ProductMapper;
import com.said.B30.businessrules.helpers.producthelpers.ProductUpdate;
import com.said.B30.dtos.productdtos.*;
import com.said.B30.infrastructure.entities.Payment;
import com.said.B30.infrastructure.entities.Product;
import com.said.B30.infrastructure.entities.Sell;
import com.said.B30.infrastructure.enums.PaymentStatus;
import com.said.B30.infrastructure.enums.ProductStatus;
import com.said.B30.infrastructure.repositories.ClientRepository;
import com.said.B30.infrastructure.repositories.ProductRepository;
import com.said.B30.infrastructure.repositories.SellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SellRepository sellRepository;
    private final ProductMapper mapper;
    private final ClientRepository clientRepository;
    private final ProductUpdate productUpdate;

    public ProductResponseDto registerProduct(ProductRequestDto productRequest) {
        return mapper.toResponse(productRepository.saveAndFlush(mapper.toEntity(productRequest)));
    }

    @Transactional
    public ProductSoldResponseDto sellProduct(Long productId, Long clientId, Integer quantity, Double establishedValue, Double initialPayment) {
        var product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(productId));
        var client = clientRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException(clientId));
        
        if (product.getQuantity() < quantity) {
             throw new DataEntryException("Estoque insuficiente. Disponível: " + product.getQuantity());
        }

        Sell sell = Sell.builder()
                .client(client)
                .quantity(quantity)
                .unitValue(establishedValue)
                .totalValue(quantity * establishedValue)
                .saleDate(LocalDate.now())
                .build();
        
        product.addSell(sell);

        if(initialPayment != null && initialPayment > 0){
            Payment payment = new Payment();
            payment.setAmount(initialPayment);
            payment.setPaymentDate(LocalDate.now());
            sell.addPayment(payment);
            
            if(initialPayment >= sell.getTotalValue()){
                sell.setPaymentStatus(PaymentStatus.PAYMENT_OK);
            } else {
                sell.setPaymentStatus(PaymentStatus.PENDING_PAYMENT);
            }
        } else {
             sell.setPaymentStatus(PaymentStatus.PENDING_PAYMENT);
        }

        product.setQuantity(product.getQuantity() - quantity);
        if (product.getQuantity() == 0) {
            product.setProductStatus(ProductStatus.SOLD_OUT);
        }
        product.setTotalValue(product.getQuantity() * product.getPreEstablishedValue());

        sellRepository.save(sell);
        productRepository.save(product);

        return mapper.toSoldResponse(sell);
    }

    public Page<ProductFullResponseDto> findAllProductsPaginated(Pageable pageable) {
        return productRepository.findAll(pageable).map(mapper::toFullResponse);
    }

    public List<ProductFullResponseDto> findProductsByDescription(String description) {
        return productRepository.findByDescriptionContainingIgnoreCase(description)
                .stream().map(mapper::toFullResponse).collect(Collectors.toList());
    }
    
    public List<ProductFullResponseDto> findProductsByClientId(Long clientId) {
        return sellRepository.findByClientId(clientId).stream()
                .map(sell -> mapper.toFullResponse(sell.getProduct()))
                .distinct()
                .collect(Collectors.toList());
    }
    
    public List<Sell> findSalesByClientId(Long clientId) {
        return sellRepository.findByClientId(clientId);
    }

    public List<ProductFullResponseDto> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(mapper::toFullResponse).toList();
    }

    public ProductFullResponseDto findProductById(Long id) {
        return mapper.toFullResponse(productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id)));
    }

    public ProductUpdateRequestDto getProductForUpdate(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return mapper.toUpdateRequest(mapper.toFullResponse(product));
    }
    
    public List<Sell> findSalesByProductId(Long productId) {
        return sellRepository.findByProductId(productId);
    }

    public ProductFullResponseDto updateData(Long id, ProductUpdateRequestDto requestDto) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        productUpdate.updateProductData(requestDto, product);
        if (product.getProductStatus() != ProductStatus.SOLD_OUT) {
             product.setTotalValue(product.getQuantity() * product.getPreEstablishedValue());
        }
        return mapper.toFullResponse(productRepository.saveAndFlush(product));
    }

    public void deleteProduct(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        if (!product.getSales().isEmpty()) {
              throw new DeletionNotAllowedException("Produto possui vendas registradas.");
        }
        productRepository.delete(product);
    }
}
