package com.said.B30.businessrules.services;

import com.said.B30.businessrules.exceptions.DeletionNotAllowedException;
import com.said.B30.businessrules.exceptions.InsufficientStockException;
import com.said.B30.businessrules.exceptions.ResourceNotFoundException;
import com.said.B30.businessrules.helpers.sellhelpers.SellMapper;
import com.said.B30.businessrules.helpers.sellhelpers.SellUpdate;
import com.said.B30.dtos.selldtos.SellUpdateRequestDto;
import com.said.B30.infrastructure.entities.Product;
import com.said.B30.infrastructure.entities.Sell;
import com.said.B30.infrastructure.enums.ProductStatus;
import com.said.B30.infrastructure.repositories.ProductRepository;
import com.said.B30.infrastructure.repositories.SellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellService {

    private final SellRepository sellRepository;
    private final ProductRepository productRepository;
    private final SellMapper mapper;
    private final SellUpdate sellUpdate;

    public SellUpdateRequestDto getSellForUpdate(Long id) {
        Sell sell = sellRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return mapper.toUpdateRequest(sell);
    }

    @Transactional
    public void updateSellData(Long id, SellUpdateRequestDto requestDto) {
        Sell sell = sellRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        int oldQuantity = sell.getQuantity();
        
        sellUpdate.updateSellData(requestDto, sell);
        
        if (requestDto.quantity() != null && requestDto.quantity() != oldQuantity) {
            Product product = sell.getProduct();
            int diff = requestDto.quantity() - oldQuantity;
            
            if (product.getQuantity() < diff) {
                 throw new InsufficientStockException("Estoque insuficiente para atualizar a venda.");
            }
            
            product.setQuantity(product.getQuantity() - diff);
            if (product.getQuantity() > 0 && product.getProductStatus() == ProductStatus.SOLD_OUT) {
                product.setProductStatus(ProductStatus.AVAILABLE);
            } else if (product.getQuantity() == 0) {
                product.setProductStatus(ProductStatus.SOLD_OUT);
            }
            product.setTotalValue(product.getQuantity() * product.getPreEstablishedValue());
            productRepository.save(product);
        }
        
        sellRepository.saveAndFlush(sell);
    }
    
    public Sell findSellById(Long id) {
        return sellRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    public void deleteSell(Long id) {
        Sell sell = sellRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        
        if (!sell.getPayments().isEmpty()) {
            throw new DeletionNotAllowedException("Não é possível deletar uma venda que possui pagamentos registrados.");
        }
        
        Product product = sell.getProduct();
        product.setQuantity(product.getQuantity() + sell.getQuantity());
        
        if (product.getQuantity() > 0 && product.getProductStatus() == ProductStatus.SOLD_OUT) {
            product.setProductStatus(ProductStatus.AVAILABLE);
        }
        product.setTotalValue(product.getQuantity() * product.getPreEstablishedValue());
        
        productRepository.save(product);
        sellRepository.delete(sell);
    }
}
