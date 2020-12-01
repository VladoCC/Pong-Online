package com.inkostilation.pong.engine;

public class Paddle extends Rectangle {

    private PlayerRole playerRole;

    private boolean controlled;

    public Paddle(float x, float y) {
        super(x, y, 20, 80);
    }

    public PlayerRole getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(PlayerRole playerRole) { this.playerRole = playerRole; }

    public void setControlled(boolean controlled) {
        this.controlled = controlled;
    }

    public boolean isControlled() {
        return controlled;
    }
}
