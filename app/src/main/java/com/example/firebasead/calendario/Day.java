package com.example.firebasead.calendario;

import java.util.ArrayList;

public class Day {
    private final int value;
    private final boolean isThisMonth;
    private final boolean isToday;
    private ArrayList<Event> dayEvents;

    public Day(int value, boolean isThisMonth, boolean isToday, ArrayList<Event> dayEvents) {
        this.value = value;
        this.isThisMonth = isThisMonth;
        this.isToday = isToday;
        this.setDayEvents(dayEvents);
    }

    public int getValue() {
        return value;
    }

    public boolean getIsThisMonth() {
        return isThisMonth;
    }

    public boolean getIsToday() {
        return isToday;
    }

    public ArrayList<Event> getDayEvents() {return dayEvents;}

    public void setDayEvents(ArrayList<Event> dayEvents) {
        this.dayEvents = dayEvents;
    }
}
