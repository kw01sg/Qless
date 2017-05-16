package com.kianboon.android.bosch.Events;

/**
 * Created by Kian Boon on 14/5/2017.
 */

public class OrderCompleteEvent {
    int orderId;

    public OrderCompleteEvent(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
