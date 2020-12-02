package com.inkostilation.pong.engine;

public class Score {

    private final int maxScoreValue = 5;
    private int player1Score, player2Score;

    public Score() {
        this.player1Score = 0;
        this.player2Score = 0;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public int getMaxScoreValue() {
        return maxScoreValue;
    }

    public void addPlayer1Score(int value)
    {
        player1Score+=value;
    }

    public void addPlayer2Score(int value)
    {
        player2Score+=value;
    }
}
