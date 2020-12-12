package com.inkostilation.pong.engine;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.*;

public class PongGame implements IUpdatable {

    private Field field;
    private Score score;
    private GameState gameState;
    private int playersNumber;
    private PlayerRole winner;
    private boolean active = true;

    private static ListMultimap<GameState, PongGame> activeGamesMap = ArrayListMultimap.create();

    public PongGame()
    {
        this.field = new Field();
        this.score = new Score();
        this.playersNumber = 0;
        this.gameState = GameState.WAITING;
        activeGamesMap.put(gameState, this);
    }

    public static PongGame getWaitingGame(int number) {
        if (activeGamesMap.isEmpty()) {
            PongGame newGame = new PongGame();
            return newGame;
        }
        else {
            if (!getGameCollection(GameState.WAITING).isEmpty()) {
                PongGame tempGame = getGame(GameState.WAITING, 0);
                return tempGame;
            } else {
                PongGame newGame = new PongGame();
                return newGame;
            }
        }
    }

    public Field getField() {
        return field;
    }

    public Score getScore() {
        return score;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void addPlayer() { ++this.playersNumber; }

    public void removePlayer() { --this.playersNumber; }

    public void start() {
        //active = true;
        activeGamesMap.remove(gameState, this);
        activeGamesMap.put(GameState.PLAYING, this);
        field.setStarted(true);
    }
    
    public void stop() {
        activeGamesMap.remove(gameState, this);
        active = false;
    }

    public void scorePoint(PlayerRole role) {
        score.addPlayerScore(role, 1);
    }

    @Override
    public void update(float delta) {
        if (active) {
            if (score.getMaxValueCount() == 0) {
                field.update(delta);
                if (!field.isBallInBounds()) {
                    scorePoint(field.getBall().getX() < 0 ? PlayerRole.FIRST : PlayerRole.SECOND);

                    field.reset();
                }
            } else {
                winner = score.getMaxedPlayers().get(0);
                stop();
            }
        }
    }

    public static List<PongGame> getGameCollection(GameState state) {
        return activeGamesMap.get(state);
    }

    public static PongGame getGame(GameState state, int index) {
        return getGameCollection(state).get(index);
    }

    public static ListMultimap<GameState, PongGame> getActiveGamesMap() {
        return activeGamesMap;
    }

    public void setControlled(PlayerRole playerRole, boolean state) {
        getField().setControlled(playerRole, state);
    }

    public boolean isControlled(PlayerRole playerRole) {
        return getField().isControlled(playerRole);
    }

    public Paddle getPaddle(PlayerRole playerRole) {
        return getField().getPaddle(playerRole);
    }

    public void setPlayerRole(PlayerRole playerRole) {
        getField().getPaddle(playerRole).setPlayerRole(playerRole);
    }
}
