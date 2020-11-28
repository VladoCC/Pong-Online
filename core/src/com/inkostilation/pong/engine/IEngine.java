package com.inkostilation.pong.engine;

import com.inkostilation.pong.commands.AbstractClientCommand;
import com.inkostilation.pong.commands.AbstractServerCommand;
import com.inkostilation.pong.exceptions.NoEngineException;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface IEngine<M> {

    void act();
    void receiveCommand(AbstractClientCommand command, M mark) throws IOException, NoEngineException;
    void sendCommand(AbstractServerCommand<IEngine<M>, M> command) throws IOException, NoEngineException;
}