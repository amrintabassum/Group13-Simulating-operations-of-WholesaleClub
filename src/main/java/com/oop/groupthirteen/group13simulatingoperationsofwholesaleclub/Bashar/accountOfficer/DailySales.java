package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.accountOfficer;

import java.io.Serializable;
import java.time.LocalDate;

public class DailySales implements Serializable {
    private String productId;
    private LocalDate date;
    private String productName;
    private int quantity;
    private double totalPrice;

    public DailySales(String productId, LocalDate date, String productName, int quantity, double totalPrice) {
        this.productId = productId;
        this.date = date;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getProductId() { return productId; }
    public LocalDate getDate() { return date; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public double getTotalPrice() { return totalPrice; }
}
