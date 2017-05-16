package com.kianboon.android.bosch.Events;

/**
 * Created by Kian Boon on 14/5/2017.
 */

public class SeatAllocationEvent {
    int sid;
    int waiting_time;

    public SeatAllocationEvent(int sid, int waiting_time) {
        this.sid = sid;
        this.waiting_time = waiting_time;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(int waiting_time) {
        this.waiting_time = waiting_time;
    }
}
