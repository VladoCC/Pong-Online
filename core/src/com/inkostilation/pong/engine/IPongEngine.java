package com.inkostilation.pong.engine;

import com.inkostilation.pong.commands.AbstractServerCommand;
import com.inkostilation.pong.exceptions.NoEngineException;

import java.io.IOException;

public interface IPongEngine<M> extends IEngine<M> {

    @Override
    void sendCommand(AbstractServerCommand<IEngine<M>, M> command) throws IOException, NoEngineException;

    void sendFieldState(M marker) throws IOException;
}
