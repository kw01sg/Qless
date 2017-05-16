package com.kianboon.android.bosch.Model;

import android.content.Context;

import com.kianboon.android.bosch.R;

import java.util.ArrayList;

/**
 * Created by Kian Boon on 14/5/2017.
 */

public class FoodItemLab {
    private Context context;
    private static FoodItemLab foodItemLab;
    private ArrayList<FoodItem> foodItems = new ArrayList<>();

    private Integer[] thumbNails = {
            R.drawable.stores_1,
            R.drawable.stores_2,
            R.drawable.stores_3,
            R.drawable.stores_4
    };

    private FoodItemLab(Context context) {
        foodItems.add(new FoodItem("Ramen Set 1", "$14.50", "Ramen with rich Tonkotsu soup."));
        foodItems.add(new FoodItem("Ramen Set 2", "$14.50", "Ramen with side meals.\nSecond variation."));
        foodItems.add(new FoodItem("Sushi Set", "$15.00", "Assort Sushi set."));
        foodItems.add(new FoodItem("Canned Drinks", "$2.00", "Canned Drinks."));
    }

    public static FoodItemLab getFoodItemLab(Context context) {
        if (foodItemLab != null) {
            return foodItemLab;
        } else {
            foodItemLab = new FoodItemLab(context);
            return foodItemLab;
        }
    }

    public int size() {
        return foodItems.size();
    }

    public FoodItem getFoodItem(int position) {
        return foodItems.get(position);
    }

    public Integer[] getThumbNails() {
        return thumbNails;
    }
}
