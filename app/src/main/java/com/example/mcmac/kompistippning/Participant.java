package com.example.mcmac.kompistippning;

/**
 * Created by McMac on 2016-06-25.
 */
public class Participant {

    long rowId;
    String eventName;
    String person;

    public Participant(long rowId, String eventName, String person){
        this.rowId = rowId;
        this.eventName = eventName;
        this.person = person;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return person;
    }

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
