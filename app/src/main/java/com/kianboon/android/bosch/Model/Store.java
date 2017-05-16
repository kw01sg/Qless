package com.kianboon.android.bosch.Model;

/**
 * Created by Kian Boon on 13/5/2017.
 */

public class Store {
    String name;
    int eatInWaitingTime;
    int takeOutWaitingTime;

    public Store(String name, int eatInWaitingTime, int takeOutWaitingTime) {
        this.name = name;
        this.eatInWaitingTime = eatInWaitingTime;
        this.takeOutWaitingTime = takeOutWaitingTime;
    }

    public int getEatInWaitingTime() {
        return eatInWaitingTime;
    }

    public void setEatInWaitingTime(int eatInWaitingTime) {
        this.eatInWaitingTime = eatInWaitingTime;
    }

    public int getTakeOutWaitingTime() {
        return takeOutWaitingTime;
    }

    public void setTakeOutWaitingTime(int takeOutWaitingTime) {
        this.takeOutWaitingTime = takeOutWaitingTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
