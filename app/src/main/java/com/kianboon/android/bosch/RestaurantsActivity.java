package com.kianboon.android.bosch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kianboon.android.bosch.Model.Restaurant;
import com.kianboon.android.bosch.Model.RestaurantsLab;
import com.squareup.picasso.Picasso;

public class RestaurantsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_edited);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("");

        GridView gridView = (GridView) findViewById(R.id.restaurants_gridview);
        gridView.setAdapter(new RestaurantsAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(RestaurantsActivity.this, SingleRestaurantActivity.class);
                Log.d(RestaurantsActivity.class.getSimpleName(), "position: " + position);
                i.putExtra(SingleRestaurantActivity.EXTRA_RESTAURANT, position);
                startActivity(i);
            }
        });

        SharedPreferences prefs = getSharedPreferences(RecommendationActivity.PREFS, MODE_PRIVATE);
        boolean have_ordered = prefs.getBoolean(RecommendationActivity.KEY_HAVE_ORDERED, false);
        Log.d(RestaurantsActivity.class.getSimpleName(), "have_ordered: " + have_ordered);

        if (have_ordered) {
            Intent i = new Intent(RestaurantsActivity.this, RecommendationActivity.class);
            startActivity(i);
        }
    }

    /**
     * Created by Kian Boon on 13/5/2017.
     */

    public static class RestaurantsAdapter extends BaseAdapter {
        private Context context;


        static class ViewHolderItem {
            ImageView imageView;
            TextView nameTextView;
            TextView waitingTimeTextView;
        }

        public RestaurantsAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return RestaurantsLab.getRestaurantsLab(context).size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolderItem viewHolder;

            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.grid_item_restaurant, parent, false);

                viewHolder = new ViewHolderItem();
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.restaurant_picture);
                viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.restaurant_name);
                viewHolder.waitingTimeTextView = (TextView) convertView.findViewById(R.id.restaurant_waiting_time);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolderItem) convertView.getTag();
            }
            final Restaurant restaurant = RestaurantsLab.getRestaurantsLab(context)
                    .getRestaurant(position);
            Picasso.with(context)
                    .load(RestaurantsLab.getRestaurantsLab(context).getThumbNail(position))
                    .fit().centerCrop().into(viewHolder.imageView);
            viewHolder.nameTextView.setText(restaurant.getName());
            viewHolder.waitingTimeTextView.setText(Integer.toString(restaurant.getGeneralWaitingTime()) + " min");
            int crowdLevelColor = RestaurantsLab.getRestaurantsLab(context).getCrowdLevelColor(restaurant.getCrowdLevel());
            viewHolder.waitingTimeTextView.setTextColor(context.getResources().getColor(crowdLevelColor));

            return convertView;
        }
    }
}
