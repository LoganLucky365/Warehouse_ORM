package org.example.warehouse_orm;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String warehouseID;
    private String warehouseName;
    private String warehouseAddress;
    private String warehousePostalCode;
    private String warehouseCity;
    private String warehouseCountry;
    private LocalDateTime timestamp;

    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    public Warehouse(Long id, String warehouseID, String warehouseName, String warehouseAddress, String warehousePostalCode, String warehouseCity, String warehouseCountry, LocalDateTime timestamp, List<Product> products) {
        this.id = id;
        this.warehouseID = warehouseID;
        this.warehouseName = warehouseName;
        this.warehouseAddress = warehouseAddress;
        this.warehousePostalCode = warehousePostalCode;
        this.warehouseCity = warehouseCity;
        this.warehouseCountry = warehouseCountry;
        this.timestamp = timestamp;
        this.products = products;
    }

    public Warehouse() {

    }

    public Long getId() {
        return id;
    }

    public String getWarehouseID() {
        return warehouseID;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public String getWarehousePostalCode() {
        return warehousePostalCode;
    }

    public String getWarehouseCity() {
        return warehouseCity;
    }

    public String getWarehouseCountry() {
        return warehouseCountry;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWarehouseID(String warehouseID) {
        this.warehouseID = warehouseID;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }

    public void setWarehousePostalCode(String warehousePostalCode) {
        this.warehousePostalCode = warehousePostalCode;
    }

    public void setWarehouseCity(String warehouseCity) {
        this.warehouseCity = warehouseCity;
    }

    public void setWarehouseCountry(String warehouseCountry) {
        this.warehouseCountry = warehouseCountry;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
