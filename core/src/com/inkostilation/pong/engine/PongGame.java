package com.inkostilation.pong.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class PongGame {

    private Field field;
    private Score score;
    private int playersNumber;
    private PlayerRole winner;
    private boolean active = true;

    private static LinkedList<PongGame> activeGamesList = new LinkedList<>();

    public PongGame()
    {
        this.field = new Field();
        this.score = new Score();
        this.playersNumber = 0;
        activeGamesList.add(this);
    }

    public static PongGame GetWaitingGame() {
        if (activeGamesList.size() == 0) {
            PongGame newGame = new PongGame();
            newGame.addPlayer();
            activeGamesList.push(newGame);
            return newGame;
        }
        else {
            if (activeGamesList.peekLast().getPlayersNumber() == 1) {
                activeGamesList.peekLast().addPlayer();
                PongGame tempGame = activeGamesList.pop();
                activeGamesList.push(tempGame);
                return tempGame;
            } else {
                PongGame newGame = new PongGame();
                newGame.addPlayer();
                activeGamesList.push(newGame);
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

    public void addPlayer() { ++this.playersNumber; }

    public void removePlayer() { --this.playersNumber; }

    public void start() {
        //active = true;
    }
    
    public void end() {
        activeGamesList.remove(this);
        active = false;
    }

    public void scorePoint(PlayerRole role) {
        score.addPlayerScore(role, 1);
    }

    public void update() {
        if (active) {
            if (score.getMaxValueCount() == 0) {
                field.run();
                if (!field.isBallInBounds()) {
                    scorePoint(field.getBall().getX() < 0 ? PlayerRole.FIRST : PlayerRole.SECOND);

                    field.reset();
                }
            } else {
                winner = score.getMaxedPlayers().get(0);
                end();
            }
        }
    }

    public static PongGame getGame(int index) {
        return activeGamesList.get(index);
    }

    public static List<PongGame> getActiveGamesList() {
        return activeGamesList;
    }
}
