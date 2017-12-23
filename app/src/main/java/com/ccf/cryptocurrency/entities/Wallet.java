package com.ccf.cryptocurrency.entities;

import java.util.ArrayList;

public class Wallet {

    private int id;
    private double amount;
    private CurrencyType currencyType;
    private ArrayList<Withdrawl> withdrawls;

    public Wallet(int id, double amount, CurrencyType currencyType) {
        this.amount = amount;
        this.currencyType = currencyType;
        this.withdrawls = new ArrayList<Withdrawl>();
        this.withdrawls.add(new Withdrawl(121));
    }

    public double getAmount() {
        return this.amount;
    }

    public String getAmountWithCurrency() {
        return this.amount + " " + this.currencyType.getShortName();
    }

    public ArrayList<Withdrawl> getWithdrawls() {
        return this.withdrawls;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }
}
