package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.Cashier;

import java.io.Serializable;

public class Customer implements Serializable {
    private String name;
    private String number;
    private String product;
    private double price;

    public Customer(String name, String number, String product, double price) {
        this.name = name;
        this.number = number;
        this.product = product;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getProduct() {
        return product;
    }

    public double getPrice() {
        return price;
    }
}
