package info.firozansari.nested_recyclerview.model;

import java.util.ArrayList;

/**
 * Created by topcashback on 20/10/2017.
 */

public class EventDates {
    private String date;
    private ArrayList<Events> eventsArrayList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Events> getEventsArrayList() {
        return eventsArrayList;
    }

    public void setEventsArrayList(ArrayList<Events> eventsArrayList) {
        this.eventsArrayList = eventsArrayList;
    }
}
