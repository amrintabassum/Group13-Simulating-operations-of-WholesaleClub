package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Amrin.inventoryManager;

import java.io.Serializable;

public class AddedStock implements Serializable {
    private String shipmentId;
    private String productName;
    private int quantity;
    private int damagedQuantity;

    public AddedStock(String shipmentId, String productName, int quantity) {
        this.shipmentId = shipmentId;
        this.productName = productName;
        this.quantity = quantity;
        this.damagedQuantity = 0; // default
    }

    public String getShipmentId() { return shipmentId; }
    public String getProductName() { return productName; }
    public int getQuantity() { return quantity; }
    public int getDamagedQuantity() { return damagedQuantity; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setDamagedQuantity(int damagedQuantity) { this.damagedQuantity = damagedQuantity; }
}
