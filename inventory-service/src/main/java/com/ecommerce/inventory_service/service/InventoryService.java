package com.ecommerce.inventory_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.inventory_service.dto.InventoryResponse;
import com.ecommerce.inventory_service.repository.InventoryReposistory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {


    private final InventoryReposistory inventoryReposistory;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
        
        return inventoryReposistory.findBySkuCodeIn(skuCode).stream().map(inventory -> 
            InventoryResponse.builder().skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity() > 0).build()
        ).toList();
    }

}
