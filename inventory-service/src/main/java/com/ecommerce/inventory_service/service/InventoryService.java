package com.ecommerce.inventory_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.inventory_service.repository.InventoryReposistory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryService {


    private final InventoryReposistory inventoryReposistory;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode){
        
        return inventoryReposistory.findBySkuCode().isPresent();
    }

}
