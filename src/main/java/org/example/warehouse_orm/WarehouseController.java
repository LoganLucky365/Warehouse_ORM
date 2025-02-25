package org.example.warehouse_orm;

import org.example.warehouse_orm.Warehouse;
import org.example.warehouse_orm.ServiceWarehouse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {

    private final ServiceWarehouse warehouseService;

    public WarehouseController(ServiceWarehouse warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public Warehouse createWarehouse(@RequestBody Warehouse warehouse) {
        return warehouseService.saveWarehouse(warehouse);
    }

    @GetMapping
    public List<Warehouse> getWarehouses() {
        return warehouseService.getAllWarehouses();
    }
}
