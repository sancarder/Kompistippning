package com.example.mcmac.kompistippning;

/**
 * Created by Mattias on 2016-06-30.
 */
public class PersonScore {

    private String name;
    private int wins;
    private int winPoints;
    private int sharedWins;
    private int sharedPoints;
    private int losses;

    public PersonScore(String name, int wins, int winPoints, int sharedWins, int sharedPoints, int losses){
        this.name = name;
        this.wins =wins;
        this.winPoints = winPoints;
        this.sharedWins =sharedWins;
        this.sharedPoints = sharedPoints;
        this.losses = losses;
    }

    public PersonScore(boolean empty){
        this.name = "";
        this.wins =0;
        this.winPoints = 0;
        this.sharedWins = 0;
        this.sharedPoints = 0;
        this.losses = 0;
    }

    public int getLosses() {
        return losses;
    }

    public void addWinPoints(int points) {
        winPoints+=points;
    }
    public void addSharedPoints(int points) {
        sharedPoints+=points;
    }
    public void setLosses(int losses) {
        this.losses = losses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void addWins(int wins) {
        this.wins+= wins;
    }

    public void addSharedWins(int wins) {
        this.sharedWins = wins;
    }
    public int getWinPoints() {
        return winPoints;
    }

    public void setWinPoints(int winPoints) {
        this.winPoints = winPoints;
    }

    public int getSharedWins() {
        return sharedWins;
    }

    public void setSharedWins(int sharedWins) {
        this.sharedWins = sharedWins;
    }

    public int getSharedPoints() {
        return sharedPoints;
    }

    public void setSharedPoints(int sharedPoints) {
        this.sharedPoints = sharedPoints;
    }


}

