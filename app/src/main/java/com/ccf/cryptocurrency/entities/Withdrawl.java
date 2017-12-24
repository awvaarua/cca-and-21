package com.ccf.cryptocurrency.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Withdrawl {

    private int id;
    private double amount;
    private String txId;
    private String toAddress;
    private Date date;

    public Withdrawl(String txId, Date date, double amount) {
        this.txId = txId;
        this.date = date;
        this.amount = amount;
    }

    public Date getDate() {
        return this.date;
    }

    public String getDateWithFormat(String format) {
        return new SimpleDateFormat(format).format(this.date);
    }

    public String getTxId() {
        return this.txId;
    }

    public double getAmount() {
        return this.amount;
    }

}
