package com.inkostilation.pong.engine;

public enum PlayerRole {
    FIRST(1), SECOND(2);

    private int number;

    PlayerRole(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}