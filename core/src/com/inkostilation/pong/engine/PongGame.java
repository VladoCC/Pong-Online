package com.inkostilation.pong.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PongGame {

    private Field field;
    private Score score;
    private PlayerRole winner;
    private boolean active = true;

    private static List<PongGame> activeGamesList = Collections.synchronizedList(new ArrayList<>());

    public PongGame()
    {
        this.field = new Field();
        this.score = new Score();
        activeGamesList.add(this);
    }

    public Field getField() {
        return field;
    }

    public Score getScore() {
        return score;
    }

    public void start() {
        //active = true;
    }
    
    public void end() {
        activeGamesList.remove(this);
        active = true;
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
