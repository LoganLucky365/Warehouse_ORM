package org.example.warehouse_orm;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Warehouse> getWarehouseByWarehouseID(String warehouseID) {
        return warehouseRepository.findByWarehouseID(warehouseID);
    }

    public Optional<Warehouse> updateWarehouse(String warehouseID, Warehouse updatedWarehouse) {
        // Abrufen des vorhandenen Warehouses
        Optional<Warehouse> existingWarehouseOpt = warehouseRepository.findByWarehouseID(warehouseID);
        if(existingWarehouseOpt.isPresent()){
            Warehouse existingWarehouse = existingWarehouseOpt.get();
            // Aktualisieren der gewünschten Felder. Hier musst du alle relevanten Felder setzen.
            existingWarehouse.setWarehouseName(updatedWarehouse.getWarehouseName());
            existingWarehouse.setWarehouseAddress(updatedWarehouse.getWarehouseAddress());
            existingWarehouse.setWarehousePostalCode(updatedWarehouse.getWarehousePostalCode());
            existingWarehouse.setWarehouseCity(updatedWarehouse.getWarehouseCity());
            existingWarehouse.setWarehouseCountry(updatedWarehouse.getWarehouseCountry());
            existingWarehouse.setTimestamp(updatedWarehouse.getTimestamp());
            // Wenn Produkte ebenfalls aktualisiert werden sollen, hier entsprechende Logik einfügen.
            warehouseRepository.save(existingWarehouse);
            return Optional.of(existingWarehouse);
        }
        return Optional.empty();
    }
}
