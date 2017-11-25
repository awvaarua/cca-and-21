package com.ccf.cryptocurrency.entities;

/**
 * Created by ssb on 25/11/17.
 */

public class Wallet {

    private double amount;
    private CurrencyType currencyType;

    public Wallet(double amount, CurrencyType currencyType) {
        this.amount = amount;
        this.currencyType = currencyType;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getAmountWithCurrency() {
        return this.amount + " " + this.currencyType.getShortName();
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }
}
