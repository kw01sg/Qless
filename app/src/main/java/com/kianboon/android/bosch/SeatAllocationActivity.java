package com.kianboon.android.bosch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kianboon.android.bosch.Events.OrderCompleteEvent;
import com.kianboon.android.bosch.Events.SeatAllocationEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SeatAllocationActivity extends AppCompatActivity {
    private int orderId = -1;
    private int sid = -1;
    private int waiting_time = -1;

    TextView button;
    TextView seatView;
    TextView descView;
    TextView orderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_allocation);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.drawable.logo_edited);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setTitle("");
        orderView = (TextView)findViewById(R.id.order_number);
        seatView = (TextView) findViewById(R.id.seat_allocation);
        descView = (TextView) findViewById(R.id.seat_description);
        button = (TextView) findViewById(R.id.allocation_update_button);

        HttpSupport support = new HttpSupport();
        support.orderFood(2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderId != -1) {
                    HttpSupport support = new HttpSupport();
                    support.getSeatThread(orderId);
                }
            }
        });
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
    public void onOrderCompleteEvent(OrderCompleteEvent event) {
        orderId = event.getOrderId();
        orderView.setText("#" + Integer.toString(orderId));

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(SeatAllocationEvent event) {
        sid = event.getSid();
        waiting_time = event.getWaiting_time();

        if (sid != -1) {
            seatView.setText(Integer.toString(sid));
            seatView.setTextSize(80);
            seatView.setTextColor(getResources().getColor(R.color.green));
            descView.setText("Please collect your order and head to your allocated seat for your meal.");
        } else {
            seatView.setText("No Seats Available.");
            seatView.setTextSize(24);
            seatView.setTextColor(getResources().getColor(R.color.red));
            descView.setText("Estimate Waiting time: " + waiting_time + " minutes.");
        }

    }


}
