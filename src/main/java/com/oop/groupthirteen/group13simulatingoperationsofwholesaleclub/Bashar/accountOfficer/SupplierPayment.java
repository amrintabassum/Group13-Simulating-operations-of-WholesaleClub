package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.accountOfficer;

import java.io.Serializable;

public class SupplierPayment implements Serializable {
    private String supplierName;
    private String paymentType;
    private double amount;

    public SupplierPayment(String supplierName, String paymentType, double amount) {
        this.supplierName = supplierName;
        this.paymentType = paymentType;
        this.amount = amount;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public double getAmount() {
        return amount;
    }
}
