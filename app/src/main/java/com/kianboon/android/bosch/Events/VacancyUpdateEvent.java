package com.kianboon.android.bosch.Events;

/**
 * Created by Kian Boon on 14/5/2017.
 */

public class VacancyUpdateEvent {
    int vacancy;

    public VacancyUpdateEvent(int vacancy) {
        this.vacancy = vacancy;
    }

    public int getVacancy() {
        return vacancy;
    }

    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }
}
