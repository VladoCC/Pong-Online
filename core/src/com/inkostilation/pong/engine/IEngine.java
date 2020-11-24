package com.inkostilation.pong.engine;

import com.inkostilation.pong.commands.AbstractClientCommand;
import com.inkostilation.pong.commands.AbstractServerCommand;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface IEngine<M> {

    void act();
    void receiveCommand(AbstractClientCommand command, M mark) throws IOException;
    void sendCommand(AbstractServerCommand<M> command) throws IOException;
    void updateField(SocketChannel channel) throws IOException;
}
