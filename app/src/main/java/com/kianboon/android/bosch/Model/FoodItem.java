package com.kianboon.android.bosch.Model;

/**
 * Created by Kian Boon on 14/5/2017.
 */

public class FoodItem {
    private String name;
    private String price;
    String description;

    public FoodItem(String name, String price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
