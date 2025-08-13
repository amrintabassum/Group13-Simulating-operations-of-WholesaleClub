package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.Cashier;

import java.io.Serializable;

public class SalesSummary implements Serializable {
    private String transaction;
    private String totalProduct;
    private String productName;
    private String price;

    public SalesSummary(String transaction, String totalProduct, String productName, String price) {
        this.transaction = transaction;
        this.totalProduct = totalProduct;
        this.productName = productName;
        this.price = price;
    }

    public String getTransaction() {
        return transaction;
    }

    public String getTotalProduct() {
        return totalProduct;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }
}
