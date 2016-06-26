package com.example.mcmac.kompistippning;

/**
 * Created by McMac on 2016-06-25.
 */
public class Game {

    long rowId;
    String eventName;
    String date;
    String teamA;
    String teamB;
//    int gameId;
    String betAmount;
    String gameType;
    String eventResult;

    public Game(long rowId, String eventName, String date, String teamA, String teamB, String betAmount, String gameType, String eventResult){
        this.rowId = rowId;
        this.eventName = eventName;
        this.date = date;
        this.teamA = teamA;
        this.teamB = teamB;
//        this.gameId = gameId;
        this.betAmount = betAmount;
        this.gameType = gameType;
        this.eventResult =eventResult;
    }

    public String getEventResult() {
        return eventResult;
    }

    public void setEventResult(String eventResult) {
        this.eventResult = eventResult;
    }

    @Override
    public String toString() {
        return teamA + " - " + teamB + " " + date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }
/*
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
*/
    public String getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(String betAmount) {
        this.betAmount = betAmount;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
}
