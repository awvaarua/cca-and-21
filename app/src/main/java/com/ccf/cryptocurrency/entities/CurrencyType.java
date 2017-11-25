package com.ccf.cryptocurrency.entities;

/**
 * Created by ssb on 25/11/17.
 */

public class CurrencyType {

    private int id;
    private String name;
    private String shortName;
    private String icon;

    public CurrencyType(int id, String name, String shortName, String icon) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.icon = icon;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getShortName() {
        return this.shortName;
    }

}
