package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.inventoryManager;

import java.io.Serializable;
import java.time.LocalDate;

public class Shipment implements Serializable {
    private String shipmentId;
    private String productName;
    private LocalDate importDate;
    private String category;
    private LocalDate expiryDate;

    public Shipment(String shipmentId, String productName, LocalDate importDate, String category, LocalDate expiryDate) {
        this.shipmentId = shipmentId;
        this.productName = productName;
        this.importDate = importDate;
        this.category = category;
        this.expiryDate = expiryDate;
    }

    public String getShipmentId() { return shipmentId; }
    public String getProductName() { return productName; }
    public LocalDate getImportDate() { return importDate; }
    public String getCategory() { return category; }
    public LocalDate getExpiryDate() { return expiryDate; }

    @Override
    public String toString() {
        return shipmentId + " | " + productName;
    }
}
