package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.accountOfficer;

import java.io.Serializable;

public class EmployeePayroll implements Serializable {
    private String employeeId;
    private String employeeName;
    private String paymentType;
    private double totalAmount;

    public EmployeePayroll(String employeeId, String employeeName, String paymentType, double totalAmount) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.paymentType = paymentType;
        this.totalAmount = totalAmount;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
