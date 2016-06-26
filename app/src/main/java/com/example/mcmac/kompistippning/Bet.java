package com.example.mcmac.kompistippning;

/**
 * Created by McMac on 2016-06-25.
 */
public class Bet {

    long rowId;
    String eventName;
    String person;
    String gameId;
    String bet;

    public Bet(long rowId, String eventName, String person, String gameId, String bet){
        this.rowId = rowId;
        this.eventName = eventName;
        this.person = person;
        this.gameId = gameId;
        this.bet = bet;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getBet() {
        return bet;
    }

    public void setBet(String bet) {
        this.bet = bet;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return bet;
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
