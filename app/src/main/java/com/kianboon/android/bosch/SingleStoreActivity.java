package com.kianboon.android.bosch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kianboon.android.bosch.Model.FoodItem;
import com.kianboon.android.bosch.Model.FoodItemLab;
import com.kianboon.android.bosch.Model.StoresLab;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingleStoreActivity extends AppCompatActivity {

    public static final String EXTRA_STORE_NAME = "name";
    public static final String EXTRA_STORE_EAT = "eat in";
    public static final String EXTRA_STORE_TAKE = "take away";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_store);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_edited);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("");

        String store_name = getIntent().getStringExtra(EXTRA_STORE_NAME);

        ListView listView = (ListView) findViewById(R.id.single_store_list_view);
        FoodItemLab foodItemLab = FoodItemLab.getFoodItemLab(this);
        listView.setAdapter(new SingleStoreAdapter(this, foodItemLab));

        TextView button = (TextView) findViewById(R.id.complete_order_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(RecommendationActivity.PREFS, MODE_PRIVATE).edit();
                editor.putBoolean(RecommendationActivity.KEY_HAVE_ORDERED, true);
                editor.commit();

                Intent i = new Intent(SingleStoreActivity.this, SeatAllocationActivity.class);
                startActivity(i);
            }
        });
    }

    private class SingleStoreAdapter extends BaseAdapter {
        Context context;
        FoodItemLab foodItemLab;

        public SingleStoreAdapter(Context context, FoodItemLab foodItemLab) {
            this.context = context;
            this.foodItemLab = foodItemLab;
        }

        @Override
        public int getCount() {
            return foodItemLab.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return foodItemLab.getFoodItem(position-1);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else return 1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (getItemViewType(position) == 0) {
                convertView = LayoutInflater.from(context).inflate(R.layout.single_store_number_of_customers, parent, false);
                TextView textView= (TextView) convertView.findViewById(R.id.select_number_of_customers);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(context, OrderActivity.class));
                    }
                });
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.single_store_food_item, parent, false);
                CircleImageView circleImageView = (CircleImageView) convertView.findViewById(R.id.food_picture);
                TextView foodName = (TextView) convertView.findViewById(R.id.food_name);
                TextView foodPrice = (TextView) convertView.findViewById(R.id.food_price);
                TextView foodDesc = (TextView) convertView.findViewById(R.id.food_description);

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(context, OrderActivity.class));
                    }
                });

                FoodItem item = (FoodItem) getItem(position);
                Picasso.with(context)
                        .load(foodItemLab.getThumbNails()[position-1])
                        .fit()
                        .centerCrop()
                        .into(circleImageView);

                foodName.setText(item.getName());
                foodPrice.setText(item.getPrice());
                foodDesc.setText(item.getDescription());
            }

            return convertView;
        }
    }
}
