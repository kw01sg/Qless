package com.kianboon.android.bosch.Model;

/**
 * Created by Kian Boon on 13/5/2017.
 */

public class Restaurant {
    private String name;
    private int generalWaitingTime;
    private int vacancy;
    private int crowdLevel;

    public Restaurant(String name, int generalWaitingTime, int crowdLevel, int vacancy) {
        this.name = name;
        this.generalWaitingTime = generalWaitingTime;
        this.crowdLevel = crowdLevel;
        this.vacancy = vacancy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGeneralWaitingTime() {
        return generalWaitingTime;
    }

    public void setGeneralWaitingTime(int generalWaitingTime) {
        this.generalWaitingTime = generalWaitingTime;
    }

    public int getCrowdLevel() {
        return crowdLevel;
    }

    public void setCrowdLevel(int crowdLevel) {
        this.crowdLevel = crowdLevel;
    }

    public int getVacancy() {
        return vacancy;
    }

    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }
}
