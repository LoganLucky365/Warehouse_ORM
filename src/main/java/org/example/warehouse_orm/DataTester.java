package org.example.warehouse_orm;

import org.example.warehouse_orm.Product;
import org.example.warehouse_orm.Warehouse;
import org.example.warehouse_orm.WarehouseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataTester implements CommandLineRunner {

    private final WarehouseRepository warehouseRepository;

    public DataTester(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void run(String... args) {
        Warehouse warehouse1 = new Warehouse();
        warehouse1.setWarehouseID("001");
        warehouse1.setWarehouseName("Linz Bahnhof");
        warehouse1.setWarehouseAddress("Bahnhofsstrasse 27/9");
        warehouse1.setWarehousePostalCode("Linz");
        warehouse1.setWarehouseCity("Linz");
        warehouse1.setWarehouseCountry("Austria");
        warehouse1.setTimestamp(LocalDateTime.now());

        Product product1 = new Product();
        product1.setProductID("00-443175");
        product1.setProductName("Bio Orangensaft Sonne");
        product1.setProductCategory("Getraenk");
        product1.setProductQuantity(2500);
        product1.setProductUnit("Packung 1L");
        product1.setWarehouse(warehouse1);

        Product product2 = new Product();
        product2.setProductID("00-871895");
        product2.setProductName("Bio Apfelsaft Gold");
        product2.setProductCategory("Getraenk");
        product2.setProductQuantity(3420);
        product2.setProductUnit("Packung 1L");
        product2.setWarehouse(warehouse1);

        warehouse1.setProducts(Arrays.asList(product1, product2));

        warehouseRepository.save(warehouse1);
    }
}
