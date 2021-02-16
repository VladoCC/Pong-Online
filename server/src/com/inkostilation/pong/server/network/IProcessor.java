package com.inkostilation.pong.server.network;

import com.inkostilation.pong.commands.AbstractResponseCommand;
import com.inkostilation.pong.exceptions.ProcessorNotStartedException;
import com.inkostilation.pong.exceptions.ProcessorStartedException;

import java.nio.channels.SocketChannel;

public interface IProcessor {

    void start(String host, int port) throws ProcessorStartedException;
    void stop();
    void processConnections() throws ProcessorNotStartedException;
    void sendMessage(AbstractResponseCommand command, SocketChannel channel);

}
