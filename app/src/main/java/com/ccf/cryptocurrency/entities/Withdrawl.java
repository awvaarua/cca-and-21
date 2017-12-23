package com.ccf.cryptocurrency.entities;

import java.util.Date;

public class Withdrawl {

    private int id;
    private double amount;
    private String txId;
    private String toAddress;
    private Date date;

    public Withdrawl(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return this.date;
    }

    public double getAmount() {
        return this.getAmount();
    }

}
