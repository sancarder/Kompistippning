package com.example.mcmac.kompistippning;

/**
 * Created by Mattias on 2016-06-30.
 */
public class PersonScore {

    private String name;
    private int wins;
    private double winPoints;
    private int sharedWins;
    private double sharedPoints;
    private int losses;

    public PersonScore(String name, int wins, double winPoints, int sharedWins, double sharedPoints, int losses){
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
    public void addSharedPoints(double points) {
        sharedPoints+=points;
    }
    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public void addWins(int wins) {
        this.wins+= wins;
    }

    public void addLoss(int loss) {
        this.losses+= loss;
    }

    public void addSharedWins(int wins) {
        this.sharedWins += wins;
    }
    public double getWinPoints() {
        return winPoints;
    }

    public int getSharedWins() {
        return sharedWins;
    }

    public double getSharedPoints() {
        return sharedPoints;
    }


}

