package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.storeManager;

import java.io.Serializable;
import java.time.LocalDate;

public class DailySales implements Serializable {
    private String productId;
    private LocalDate date;
    private String productName;
    private int quantity;
    private double unitPrice;

    public DailySales(String productId, LocalDate date, String productName, int quantity, double unitPrice) {
        this.productId = productId;
        this.date = date;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getProductId() { return productId; }
    public LocalDate getDate() { return date; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotalPrice() { return quantity * unitPrice; }
}
