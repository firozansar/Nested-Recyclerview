package info.firozansari.nested_recyclerview.model;

import java.util.ArrayList;

/**
 * Created by topcashback on 20/10/2017.
 */

public class EventInformation {
    private ArrayList<EventDates> eventsDatesList = new ArrayList<>();

    public ArrayList<EventDates> getEventsDatesList() {
        return eventsDatesList;
    }

    public void setEventsDatesList(ArrayList<EventDates> eventsDatesList) {
        this.eventsDatesList = eventsDatesList;
    }
}
