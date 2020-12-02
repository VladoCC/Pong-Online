package com.inkostilation.pong.engine;

import java.nio.channels.SocketChannel;
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

    private static List<Game> gameList = new ArrayList<>();

    public Field getField() {
        return field;
    }

    public Score getScore() {
        return score;
    }

    public void start() {
        gameList.add(this);
    }
    
    public void end() {
        gameList.remove(this);
    }

    public boolean endOfSet() {
        if (!field.getBall().isInBounds(field.getWidth()))
        {
            if (field.getBall().getX() < 0)
                score.addPlayer1Score(1);
            else
                score.addPlayer2Score(1);
            return true;
        }
        return false;
    }

    public void update() {
        while (this.score.getPlayer1Score() != score.getMaxScoreValue() || this.score.getPlayer2Score() != score.getMaxScoreValue()) {
            field.run();
            if (this.endOfSet()) {
                field.getPaddle1().setControlled(false);
                field.getPaddle2().setControlled(false);
            }
        }
        winner = (this.score.getPlayer1Score() == score.getMaxScoreValue()) ? PlayerRole.FIRST : PlayerRole.SECOND;
        this.end();
    }

    public Game getGameAt(int index) {
        return gameList.get(index);
    }
}
