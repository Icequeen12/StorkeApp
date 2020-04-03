package com.example.pamela.udar;

public class Results {

    private int playerResult;
    private int time;
    private String date;

    public Results(int playerResult, int time, String date) {
        this.playerResult = playerResult;
        this.time = time;
        this.date = date;
    }

    public int getPlayerResult() {
        return playerResult;
    }

    public void setPlayerResult(int playerResult) {
        this.playerResult = playerResult;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}