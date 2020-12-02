package com.inkostilation.pong.engine;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Field field;
    private Score score;

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

    public Game getGameAt(int index) {
        return gameList.get(index);
    }
}
