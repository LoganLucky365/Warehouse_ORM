package org.example.warehouse_orm;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceWarehouse {

    private final WarehouseRepository warehouseRepository;

    public ServiceWarehouse(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse saveWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }
}
