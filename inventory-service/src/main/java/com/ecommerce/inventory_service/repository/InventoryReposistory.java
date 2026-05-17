package com.ecommerce.inventory_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.inventory_service.model.Inventory;

public interface InventoryReposistory extends JpaRepository<Inventory,Long>{

    Optional<Inventory> findBySkuCode();

}
