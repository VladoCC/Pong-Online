package com.inkostilation.pong.engine;

public class PlayerData {

    private Game game;
    private PlayerRole playerRole;


    public PlayerData(Game game, PlayerRole playerRole) {
        this.game = game;
        this.playerRole = playerRole;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public PlayerRole getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(PlayerRole playerRole) {
        this.playerRole = playerRole;
    }
}
