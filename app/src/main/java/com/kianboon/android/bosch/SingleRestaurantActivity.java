package com.kianboon.android.bosch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kianboon.android.bosch.Events.VacancyUpdateEvent;
import com.kianboon.android.bosch.Model.Restaurant;
import com.kianboon.android.bosch.Model.RestaurantsLab;
import com.kianboon.android.bosch.Model.Store;
import com.kianboon.android.bosch.Model.StoresLab;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;

public class SingleRestaurantActivity extends AppCompatActivity {
    public static String EXTRA_RESTAURANT = "single_restaurant";
    private ListView listView;
    private TextView vacancyTextView;
    private TextView waitingTimeTextView;
    private int vacancy = -1;
    private int restaurant_position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_restaurant);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_edited);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("");

        HttpSupport support = new HttpSupport();
        support.getVacancy();

        restaurant_position = getIntent().getIntExtra(EXTRA_RESTAURANT, -1);

        if (restaurant_position != -1) {
            listView = (ListView) findViewById(R.id.single_restaurant_list_view);
            StoresLab storesLab = new StoresLab(restaurant_position);
            listView.setAdapter(new SingleRestaurantAdapter(this, storesLab));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(VacancyUpdateEvent event) {
        vacancy = event.getVacancy();
        if (vacancyTextView != null ) {
            vacancyTextView.setText(Integer.toString(vacancy) + " seats available");
            waitingTimeTextView.setText("Est. Waiting Time: "
                    + Integer.toString(RestaurantsLab.getRestaurantsLab(this).getRestaurant(restaurant_position).getGeneralWaitingTime())
                    + " minutes");
            if (vacancy == 0) {
                vacancyTextView.setTextColor(getResources().getColor(R.color.red));
            } else {
                vacancyTextView.setTextColor(getResources().getColor(R.color.green));
            }
        }
    }

    private class SingleRestaurantAdapter extends BaseAdapter {
        private Context context;
        private StoresLab storesLab;

        public SingleRestaurantAdapter(Context context, StoresLab storesLab) {
            this.context = context;
            this.storesLab = storesLab;
        }

        @Override
        public int getCount() {
            // 2 for other items besides list
            return 3 + storesLab.size();
        }

        @Override
        public Object getItem(int position) {
            return storesLab.getStores().get(position-3);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public int getItemViewType(int position) {
            switch (position) {
                case 0: return 0;
                case 1: return 1;
                case 2: return 2;
                default: return 3;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (getItemViewType(position) == 0) {
                convertView = LayoutInflater.from(context).inflate(R.layout.single_restaurant_profile, parent, false);
                RestaurantsLab restaurantsLab = RestaurantsLab.getRestaurantsLab(context);
                Restaurant restaurant = restaurantsLab.getRestaurant(restaurant_position);
                ImageView restaurant_picture = (ImageView) convertView.findViewById(R.id.single_restaurant_picture);
                TextView restaurant_name = (TextView) convertView.findViewById(R.id.single_restaurant_name);

                restaurant_name.setText(restaurant.getName());

                Picasso.with(context)
                        .load(RestaurantsLab.getRestaurantsLab(context).getThumbNail(restaurant_position))
                        .fit()
                        .centerCrop()
                        .into(restaurant_picture);

                return convertView;
            }

            if (getItemViewType(position) == 1) {
                convertView = LayoutInflater.from(context).inflate(R.layout.single_restaurant_information, parent, false);

                vacancyTextView = (TextView) convertView.findViewById(R.id.single_restaurant_information_vacancy);
                if (vacancy != -1) {
                    vacancyTextView.setText(Integer.toString(vacancy) + " seats available");
                    waitingTimeTextView.setText("Est. Waiting Time: "
                            + Integer.toString(RestaurantsLab.getRestaurantsLab(context).getRestaurant(restaurant_position).getGeneralWaitingTime())
                            + " minutes");
                    if (vacancy == 0) {
                        vacancyTextView.setTextColor(getResources().getColor(R.color.red));
                    } else {
                        vacancyTextView.setTextColor(getResources().getColor(R.color.green));
                    }
                }

                waitingTimeTextView = (TextView) convertView.findViewById(R.id.single_restaurant_information_waiting_time);

            }

            if (getItemViewType(position) == 2) {
                convertView = LayoutInflater.from(context).inflate(R.layout.single_restaurant_update_button, parent, false);
                TextView button = (TextView) convertView.findViewById(R.id.store_update_button);

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // update information here
                        Log.d(HttpSupport.class.getSimpleName(), "update!");
                        HttpSupport support = new HttpSupport();
                        support.getVacancy();
                    }
                });
            }

            if (getItemViewType(position) == 3) {
                convertView = LayoutInflater.from(context).inflate(R.layout.single_restaurant_store, parent, false);
                CircleImageView circleImageView = (CircleImageView) convertView.findViewById(R.id.store_picture);
                TextView storeEatInWaitingTime = (TextView) convertView.findViewById(R.id.store_eat_in_waiting_time);
                TextView storeTakeAwayWaitingTime = (TextView) convertView.findViewById(R.id.store_takeaway_waiting_time);
                TextView storeName = (TextView) convertView.findViewById(R.id.store_name);

                // populate here
                final Store store = (Store) getItem(position);
                Picasso.with(context)
                        .load(storesLab.getThumbNails()[position-3])
                        .fit()
                        .centerCrop()
                        .into(circleImageView);

                storeEatInWaitingTime.setText("Eat In: " + Integer.toString(store.getEatInWaitingTime()) + "min");
                storeTakeAwayWaitingTime.setText("Takeaway: " + Integer.toString(store.getTakeOutWaitingTime()) + "min");
                storeName.setText(store.getName());

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, SingleStoreActivity.class);
                        i.putExtra(SingleStoreActivity.EXTRA_STORE_NAME, store.getName());
                        i.putExtra(SingleStoreActivity.EXTRA_STORE_EAT, store.getEatInWaitingTime());
                        i.putExtra(SingleStoreActivity.EXTRA_STORE_TAKE, store.getTakeOutWaitingTime());
                        context.startActivity(i);
                    }
                });
            }


            return convertView;
        }
    }
}
