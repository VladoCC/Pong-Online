package com.inkostilation.pong.engine;

import com.inkostilation.pong.commands.AbstractClientCommand;
import com.inkostilation.pong.commands.AbstractServerCommand;
import com.inkostilation.pong.exceptions.NoEngineException;

import java.io.IOException;

public class NullEngine<M> implements IEngine<M> {

    public static final NullEngine INSTANCE = new NullEngine();


    private NullEngine() {
    }

    @Override
    public void act() {

    }

    @Override
    public void receiveCommand(AbstractClientCommand command, Object mark) throws IOException, NoEngineException {

    }

    @Override
    public void sendCommand(AbstractServerCommand<IEngine<M>, M> command) throws IOException, NoEngineException {

    }
}
