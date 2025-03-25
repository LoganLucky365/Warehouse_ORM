package org.example.warehouse_orm;

import org.example.warehouse_orm.Warehouse;
import org.example.warehouse_orm.ServiceWarehouse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final ServiceWarehouse warehouseService;
    private final ProductRepository productRepository;

    public WarehouseController(ServiceWarehouse warehouseService, ProductRepository productRepository) {
        this.warehouseService = warehouseService;
        this.productRepository = productRepository;
    }

    @PostMapping
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.saveWarehouse(warehouse);
    }

    @GetMapping
    public List<Warehouse> getWarehouses() {
        return warehouseService.getAllWarehouses();
    }

    @GetMapping("/{warehouseID}")
    public ResponseEntity<Warehouse> getWarehouseByWarehouseID(@PathVariable String warehouseID) {
        return warehouseService.getWarehouseByWarehouseID(warehouseID)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{warehouseID}/products/{productID}")
    public ResponseEntity<Product> getProductByWarehouseAndProductID(@PathVariable String warehouseID,
                                                                     @PathVariable String productID) {
        return productRepository.findByWarehouse_WarehouseIDAndProductID(warehouseID, productID)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{warehouseID}")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable String warehouseID, @RequestBody Warehouse updatedWarehouse) {
        return warehouseService.updateWarehouse(warehouseID, updatedWarehouse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
