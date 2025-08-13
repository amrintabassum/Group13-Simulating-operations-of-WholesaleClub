package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.accountOfficer;

import java.io.Serializable;

public class InventoryPurchase implements Serializable {
    private String productId;
    private String productName;
    private double price;
    private String customerName;
    private String customerNumber;

    public InventoryPurchase(String productId, String productName, double price, String customerName, String customerNumber) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.customerName = customerName;
        this.customerNumber = customerNumber;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public String getCustomerName() { return customerName; }
    public String getCustomerNumber() { return customerNumber; }
}
