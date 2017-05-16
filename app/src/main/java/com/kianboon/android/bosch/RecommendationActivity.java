package com.kianboon.android.bosch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kianboon.android.bosch.Model.RestaurantsLab;
import com.kianboon.android.bosch.Model.Store;
import com.kianboon.android.bosch.Model.StoresLab;
import com.squareup.picasso.Picasso;

public class RecommendationActivity extends AppCompatActivity {
    public static String PREFS = "recommendation";
    public static String KEY_HAVE_ORDERED = "order?";

    private ImageView recommendationPicture;
    private TextView recommendationName;
    private TextView orderButton;
    private TextView desc;
    private int storeNumber = 1;
    private Store store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_edited);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("");

        recommendationPicture = (ImageView) findViewById(R.id.recommendations_store_picture);
        recommendationName = (TextView) findViewById(R.id.recommendations_store_name);
        orderButton = (TextView) findViewById(R.id.order_recommendation_button);
        desc = (TextView) findViewById(R.id.recommendations_desc);

        StoresLab storesLab = new StoresLab(1);
        store = storesLab.getStores().get(storeNumber);
        Picasso.with(this)
                .load(storesLab.getThumbNails()[storeNumber])
                .fit()
                .centerCrop()
                .into(recommendationPicture);
        recommendationName.setText(store.getName());
        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RecommendationActivity.this, SingleStoreActivity.class);
                i.putExtra(SingleStoreActivity.EXTRA_STORE_NAME, store.getName());
                i.putExtra(SingleStoreActivity.EXTRA_STORE_EAT, store.getEatInWaitingTime());
                i.putExtra(SingleStoreActivity.EXTRA_STORE_TAKE, store.getTakeOutWaitingTime());
                startActivity(i);
            }
        });

        desc.setText("Interested in Japanese food?\n" + store.getName() + " offers the best " +
                "Japanese food selection with their " + "cheap and delicious set meals.");
    }
}
