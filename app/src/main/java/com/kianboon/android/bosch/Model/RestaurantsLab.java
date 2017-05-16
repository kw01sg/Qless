package com.kianboon.android.bosch.Model;

import android.content.Context;

import com.kianboon.android.bosch.R;

import java.util.ArrayList;

/**
 * Created by Kian Boon on 13/5/2017.
 */

public class RestaurantsLab {
    private static RestaurantsLab restaurantLab;
    private ArrayList<Restaurant> restaurants;

    private Integer[] thumbNails = {
            R.drawable.sample_picture_1,
            R.drawable.sample_picture_2,
            R.drawable.sample_picture_3,
            R.drawable.sample_picture_4,
            R.drawable.sample_picture_5,
            R.drawable.sample_picture_6
    };

    private RestaurantsLab(Context context) {
        restaurants = new ArrayList<>();
        restaurants.add(new Restaurant("Jurong Point FoodCourt", 5, 0, -1));
        restaurants.add(new Restaurant("Northpoint Kopitiam", 20, 2, -1));
        restaurants.add(new Restaurant("AMK Hub Foodfare", 10, 1, -1));
        restaurants.add(new Restaurant("Hougang Mall", 10, 0, -1));
        restaurants.add(new Restaurant("Junction 8", 17, 2, -1));
        restaurants.add(new Restaurant("NTU Koufu", 15, 2, -1));
    }

    public static RestaurantsLab getRestaurantsLab(Context context) {
        if (restaurantLab == null) {
            restaurantLab = new RestaurantsLab(context);
        }
        return restaurantLab;
    }

    public Restaurant getRestaurant(int position) {
        return restaurants.get(position);
    }

    public int size() {
        return restaurants.size();
    }

    public Integer getThumbNail(int position) {
        return thumbNails[position];
    }

    public int getCrowdLevelColor(int crowdLevel) {
        switch (crowdLevel) {
            case 2:
                return R.color.crowd_level_2;
            case 1:
                return R.color.crowd_level_1;
            case 0:
                return R.color.crowd_level_0;
        }

        return 0;
    }
}
