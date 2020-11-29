package com.inkostilation.pong.engine;

public enum PlayerRole {
    DENIED(0), FIRST(1), SECOND(2);

    public int number;

    PlayerRole(int number) {
        this.number = number;
    }

}