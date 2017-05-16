package com.kianboon.android.bosch.Model;

import android.content.Context;

import com.kianboon.android.bosch.R;

import java.util.ArrayList;

/**
 * Created by Kian Boon on 13/5/2017.
 */

public class StoresLab {
    private ArrayList<Store> stores = new ArrayList<>();
    private int restaurant_id;
    private Integer[] thumbNails = {
            R.drawable.sample_stores_1,
            R.drawable.sample_stores_2,
            R.drawable.sample_stores_3,
            R.drawable.sample_stores_4,
            R.drawable.sample_stores_5,
            R.drawable.sample_stores_6,
    };

    public StoresLab(int restaurant_id) {
        this.restaurant_id = restaurant_id;
        stores.add(new Store("The Ramen Stall", 20, 10));
        stores.add(new Store("itadakimasu", 15, 7));
        stores.add(new Store("Ah Hoe Mee Pok", 10, 5));
        stores.add(new Store("Hainan Chicken Rice", 4, 3));
        stores.add(new Store("Lucky Fishball Soup", 5, 4));
        stores.add(new Store("Beverages", 1, 1));
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int size() {
        return stores.size();
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public Integer[] getThumbNails() {
        return thumbNails;
    }

    public void setThumbNails(Integer[] thumbNails) {
        this.thumbNails = thumbNails;
    }
}
