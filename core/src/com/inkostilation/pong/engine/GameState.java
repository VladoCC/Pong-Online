package com.inkostilation.pong.engine;

public enum GameState {
    WAITING(0), CONFIRMATION(1), PLAYING(2), INTERRUPTED(3), AFTER_GOAL_CONFIRMATION(4);

    public final int state;

    GameState(int state) {
        this.state = state;
    }
}
