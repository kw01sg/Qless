package com.kianboon.android.bosch;

import android.util.Log;

import com.kianboon.android.bosch.Events.OrderCompleteEvent;
import com.kianboon.android.bosch.Events.SeatAllocationEvent;
import com.kianboon.android.bosch.Events.VacancyUpdateEvent;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Reader;

/**
 * Created by Kian Boon on 13/5/2017.
 */

public class HttpSupport {
    private static final String SERVER_URL = "";

    public void orderFood(int foodId) {
        new OrderFoodThread(foodId).start();
    }

    public void getVacancy() {
        new GetVacancyThread().start();
    }

    public void getSeatThread(int orderId) {
        new GetSeatThread(orderId).start();
    }

    private class OrderFoodThread extends Thread {
        private int foodId;
        String url;
        String ADD_NEW_ORDER_PATH = "";

        public OrderFoodThread(int foodId) {
            this.foodId = foodId;
            Log.d(OrderFoodThread.class.getSimpleName(), "foodId: " + foodId);
            url = SERVER_URL + ADD_NEW_ORDER_PATH + foodId + "/";
        }

        @Override
        public void run() {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(url).build();
                Response response = client.newCall(request).execute();
                Log.d(OrderFoodThread.class.getSimpleName(), "sending out http request");

                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.d(OrderFoodThread.class.getSimpleName(), "successful response: " + result);
                    JSONObject reader = new JSONObject(result);
                    int orderId = Integer.parseInt(reader.getString("order_id"));

                    EventBus.getDefault().post(new OrderCompleteEvent(orderId));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class GetVacancyThread extends Thread {
        private String VACANCY_PATH = "";
        @Override
        public void run() {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(SERVER_URL + VACANCY_PATH).build();
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.d(GetVacancyThread.class.getSimpleName(), "successful response: " + result);
                    JSONObject reader = new JSONObject(result);
                    int vacancy = Integer.parseInt(reader.getString("seats_available"));

                    EventBus.getDefault().post(new VacancyUpdateEvent(vacancy));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class GetSeatThread extends Thread {
        int orderId;
        final private String NOTIFY_VACANT_SEAT_PATH = "";

        public GetSeatThread(int orderId) {
            this.orderId = orderId;
        }

        @Override
        public void run() {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url(SERVER_URL + NOTIFY_VACANT_SEAT_PATH + orderId).build();
                Response response = client.newCall(request).execute();

                if (response.isSuccessful()) {
                    String result = response.body().string();
                    Log.d(GetVacancyThread.class.getSimpleName(), "successful response: " + result);
                    JSONObject reader = new JSONObject(result);
                    int sid = Integer.parseInt(reader.getString("sid"));
                    int waiting_time = Integer.parseInt(reader.getString("wait"));

                    EventBus.getDefault().post(new SeatAllocationEvent(sid, waiting_time));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
