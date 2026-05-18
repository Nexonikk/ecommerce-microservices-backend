package com.ecommerce.inventory_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.inventory_service.dto.InventoryResponse;
import com.ecommerce.inventory_service.service.InventoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku-code}")
    public List<InventoryResponse> isInStock(@RequestParam("sku-code") List<String> skuCode){
     return inventoryService.isInStock(skuCode);
    }

}
