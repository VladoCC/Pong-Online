package com.inkostilation.pong.engine;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Field field;
    private Score score;
    private PlayerRole winner;

    public Game()
    {
        this.field = new Field();
        this.score = new Score();
    }

    private static List<Game> activeGamesList = new ArrayList<>();

    public Field getField() {
        return field;
    }

    public Score getScore() {
        return score;
    }

    public void start() {
        activeGamesList.add(this);
    }
    
    public void end() {
        activeGamesList.remove(this);
    }

    public void scorePoint(PlayerRole role) {
        score.addPlayerScore(role, 1);
    }

    public void update() {
        while (score.getMaxValueCount() == 0) {
            field.run();
            if (!field.isBallInBounds()) {
                scorePoint(field.getBall().getX() < 0? PlayerRole.FIRST : PlayerRole.SECOND);
                field.getPaddle1().setControlled(false);
                field.getPaddle2().setControlled(false);
            }
        }
        winner = score.getMaxedPlayers().get(0);
        end();
    }

    public Game getGameAt(int index) {
        return activeGamesList.get(index);
    }
}
