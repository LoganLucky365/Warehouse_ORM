# Einleitung

Im Zuge des Dezentralen Systeme Unterricht in diesen Jahr sollen wir uns mit relationalen Datenbanken vertraut machen. Dafür erstellen wir ein simples Spring Boot Programm mit dessen Hilfe wir mit einer MySQL Datenbank zusammenarbeiten und Daten bearbeiten beziehungsweise auslesen.

# Vorgegebene Datenstruktur

```xml
<warehouseData>
    <warehouseID>001</warehouseID>
    <warehouseName>Linz Bahnhof</warehouseName>
    <warehouseAddress>Bahnhofsstrasse 27/9</warehouseAddress>
    <warehousePostalCode>Linz</warehousePostalCode>
    <warehouseCity>Linz</warehouseCity>
    <warehouseCountry>Austria</warehouseCountry>
    <timestamp>2021-09-12 08:52:39.077</timestamp>
    <productData>
         <product>
             <productID>00-443175</productID>
             <productName>Bio Orangensaft Sonne</productName>
             <productCategory>Getraenk</productCategory>
             <productQuantity>2500</productQuantity>
             <productUnit>Packung 1L</productUnit>
         </product>
         <product>
             <productID>00-871895</productID>
             <productName>Bio Apfelsaft Gold</productName>
             <productCategory>Getraenk</productCategory>
             <productQuantity>3420</productQuantity>
             <productUnit>Packung 1L</productUnit>
         </product>
    </productData>
</warehouseData>
```
# Dokumentation der Aufgabenstellungen

### GKü/GKv

Zuerst muss ein Spring Boot Projekt und eine simple docker Compose (Für die mySQL DB) erstellt werden.

```d
services:  
  mysql:  
    container_name: 'mysql_Warehouse_ORM'  
    image: 'mysql:latest'  
    environment:  
      - 'MYSQL_DATABASE=warehouse'  
      - 'MYSQL_PASSWORD=admin'  
      - 'MYSQL_ROOT_PASSWORD=admin'  
      - 'MYSQL_USER=admin'  
    ports:  
      - '3306:3306'
```

Natürlich müssen wir ebenfalls unsere applikations.properties Datei anpassen:

```
spring.application.name=Warehouse_ORM  
spring.datasource.url=jdbc:mysql://mysql_Warehouse_ORM:3306/warehouse?useSSL=false&serverTimezone=UTC  
spring.datasource.username=admin  
spring.datasource.password=admin  
spring.jpa.hibernate.ddl-auto=update  
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect  
spring.jpa.show-sql=true
```


Als nächstes muss ich mir die zwei Entety Klassen für mein Warehouse und meine Produkte (diese kann ich mir als "Blaupausen" vorstellen) schreiben.

```java
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

// Hier fehlen noch Getter/Settter
```

```java
@Entity  
public class Product {  
  
    @Id  
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;  
  
    private String productID;  
    private String productName;  
    private String productCategory;  
    private int productQuantity;  
    private String productUnit;  
  
    @ManyToOne  
    @JoinColumn(name = "warehouse_id")  
    private Warehouse warehouse;

// Hier fehlen noch Getter/Setter
```

Als nächstes müssen die beiden Repository Klassen geschrieben werden.

```java
package org.example.warehouse_orm;  
  
import org.example.warehouse_orm.Product;  
import org.springframework.data.jpa.repository.JpaRepository;  
  
public interface ProductRepository extends JpaRepository<Product, Long> {  
}
```

```java
package org.example.warehouse_orm;  
  
import org.example.warehouse_orm.Warehouse;  
import org.springframework.data.jpa.repository.JpaRepository;  
  
public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {  
}
```

Nun muss ich noch den Service für mein Warehouse schreiben.

```java
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
```

Zu guter letzt müssen wir natürlich noch den Spring Controller implementieren.

```java
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
```

Nun befüllen wir unsere Datenbank noch mit einigen Dummydaten um die Funktion unseres Programmes zu testen.

```java
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
```

# Testen

```cmd
 curl -d '{"warehousID":"2", "warehousName":"Test"}' -H "Content-Type: application/json" -X POST http://127.0.0.1:8080/api/warehouses
```


# Ausarbeitung der Fragestellungen

- **What is ORM and how is JPA used?**
  **ORM (Object-Relational Mapping):**  
  Ermöglicht es, Java-Objekte direkt auf relationale Datenbanktabellen abzubilden. Dadurch wird der Umgang mit Datenbanken abstrahiert und du kannst mit Objekten arbeiten, statt mit SQL-Statements.
  **JPA (Java Persistence API):**  
  Ein Standard-API in Java, das ORM-Funktionalitäten bereitstellt. Anhand von JPA-Annotationen (z. B. `@Entity`, `@Id`, `@OneToMany`) definierst du, wie deine Java-Klassen in Datenbanktabellen gemappt werden. In unserem Programm werden z. B. `Warehouse` und `Product` als Entitäten definiert und ihre Beziehungen über Annotationen hergestellt.
- **What is the application.properties used for and where must it be stored?**
  In der `application.properties` legst du Konfigurationen für deine Spring Boot Anwendung fest – z. B. Datenbankverbindungsdetails, JPA-Einstellungen, Logging und andere Framework-bezogene Properties.
- **Which annotations are frequently used for entity types? Which key points must be observed?**

- `@Entity`: Markiert eine Klasse als persistente Entität.
- `@Id`: Definiert das Primärschlüsselfeld.
- `@GeneratedValue`: Automatische Generierung von Primärschlüsseln.
- `@Column`: Zum Festlegen von Spalteneigenschaften (optional).
- `@OneToMany` / `@ManyToOne`: Definieren Beziehungen zwischen Entitäten.
- `@JoinColumn`: Gibt die Join-Spalte bei Beziehungen an.

- **What methods do you need for CRUD operations?**
  CRUD steht für Create, Read, Update, Delete.
  Benötigte Methoden (z. B. im Spring Data JPA Repository):
  - **Create/Update:** `save()` – legt neue Einträge an oder aktualisiert bestehende Datensätze.
  - **Read:** `findById()`, `findAll()` – zum Abrufen einzelner oder aller Datensätze.
  - **Delete:** `delete()`, `deleteById()` – zum Entfernen von Datensätzen.
  In unserem Beispiel wird im Controller `warehouseRepository.save()` verwendet, um Warehouses (und damit die zugehörigen Produkte) anzulegen bzw. zu aktualisieren, und `findAll()` zum Abrufen der Daten.