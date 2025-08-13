package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.accountOfficer;

import java.io.Serializable;
import java.time.LocalDate;

public class RevenueReport implements Serializable {
    private String productName;
    private double price;
    private LocalDate date;
    private double revenue;

    public RevenueReport(String productName, double price, LocalDate date, double revenue) {
        this.productName = productName;
        this.price = price;
        this.date = date;
        this.revenue = revenue;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }
}
