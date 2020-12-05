package com.inkostilation.pong.commands;

import com.inkostilation.pong.engine.Score;
import com.inkostilation.pong.exceptions.NoEngineException;

import java.io.IOException;

public class ResponseScoreCommand extends AbstractResponseCommand {

    private Score score;

    public ResponseScoreCommand(Score score) {
        this.score = score;
    }

    @Override
    public void execute() throws IOException, NoEngineException {
        getNotifier().notifyObservers(score);
    }
}
