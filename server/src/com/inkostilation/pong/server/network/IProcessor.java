package com.inkostilation.pong.server.network;

import com.inkostilation.pong.commands.AbstractClientCommand;
import com.inkostilation.pong.commands.AbstractServerCommand;

import java.nio.channels.SocketChannel;

public interface IProcessor {

    void start();
    void stop();
    void processConnections();
    void sendMessage(AbstractClientCommand command, SocketChannel channel);
}
