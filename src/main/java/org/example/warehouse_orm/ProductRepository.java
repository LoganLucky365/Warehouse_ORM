package org.example.warehouse_orm;

import org.example.warehouse_orm.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}