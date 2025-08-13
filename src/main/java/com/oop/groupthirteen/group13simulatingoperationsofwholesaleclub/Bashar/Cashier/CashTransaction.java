package com.oop.groupthirteen.group13simulatingoperationsofwholesaleclub.Bashar.Cashier;


import java.io.Serializable;

public class CashTransaction implements Serializable {
    private String cashIn;
    private String cashOut;
    private String validateDate;

    public CashTransaction(String cashIn, String cashOut, String validateDate) {
        this.cashIn = cashIn;
        this.cashOut = cashOut;
        this.validateDate = validateDate;
    }

    public String getCashIn() { return cashIn; }
    public String getCashOut() { return cashOut; }
    public String getValidateDate() { return validateDate; }
}

