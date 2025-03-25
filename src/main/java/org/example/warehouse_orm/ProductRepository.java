package org.example.warehouse_orm;

import org.example.warehouse_orm.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByWarehouse_WarehouseIDAndProductID(String warehouseID, String productID);
}