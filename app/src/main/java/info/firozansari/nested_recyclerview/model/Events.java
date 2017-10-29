package info.firozansari.nested_recyclerview.model;

import java.util.Calendar;

/**
 * Created by topcashback on 20/10/2017.
 */

public class Events {
    private String eventId;
    private String eventName;
    public Calendar creationDate;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }
}
