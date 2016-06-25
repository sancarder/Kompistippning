package com.example.mcmac.kompistippning;

/**
 * Created by McMac on 2016-06-25.
 */
public class Competition {

    long rowId;
    String eventName;

    public Competition(long rowId, String eventName){
        this.rowId = rowId;
        this.eventName = eventName;
    }

    @Override
    public String toString() {
        return eventName;
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
