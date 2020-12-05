package com.inkostilation.pong.commands;

import com.inkostilation.pong.engine.IPongEngine;

import java.io.IOException;

public class RequestScoreCommand<M> extends AbstractPongCommand<M> {
    @Override
    void execute(IPongEngine<M> engine) throws IOException {
        engine.sendScore(getMarker());
    }
}
