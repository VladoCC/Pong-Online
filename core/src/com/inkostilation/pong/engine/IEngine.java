package com.inkostilation.pong.engine;

import com.inkostilation.pong.commands.AbstractResponseCommand;
import com.inkostilation.pong.commands.AbstractRequestCommand;
import com.inkostilation.pong.exceptions.NoEngineException;

import java.io.IOException;

public interface IEngine<M> {

    void act();
    void receiveCommand(AbstractResponseCommand command, M mark) throws IOException, NoEngineException;
    void sendCommand(AbstractRequestCommand<IEngine<M>, M>... command) throws IOException, NoEngineException;
}
