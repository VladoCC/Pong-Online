package com.inkostilation.pong.server.engine;

import com.inkostilation.pong.commands.AbstractClientCommand;
import com.inkostilation.pong.commands.AbstractServerCommand;
import com.inkostilation.pong.commands.ClientMessageCommand;
import com.inkostilation.pong.engine.IEngine;
import com.inkostilation.pong.server.network.NetworkProcessor;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class GameEngine implements IEngine<SocketChannel> {

    private static IEngine<SocketChannel> instance = null;

    private GameEngine() {
    }

    public static IEngine<SocketChannel> getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    @Override
    public void act() {

    }

    @Override
    public void receiveCommand(AbstractClientCommand command, SocketChannel channel) throws IOException {
        NetworkProcessor.getInstance().sendMessage(command, channel);
    }

    @Override
    public void sendCommand(AbstractServerCommand<SocketChannel> command) throws IOException {
        // todo temp code
        command.execute();
        receiveCommand(new ClientMessageCommand("pong"), command.getMarker());
    }
}
