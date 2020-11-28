package com.inkostilation.pong.server.engine;

import com.inkostilation.pong.commands.*;
import com.inkostilation.pong.engine.*;
import com.inkostilation.pong.exceptions.NoEngineException;
import com.inkostilation.pong.server.network.NetworkProcessor;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class PongEngine implements IPongEngine<SocketChannel> {

    private static IPongEngine<SocketChannel> instance = null;

    private Field field;

    private PongEngine() {
        field = new Field();
    }

    public static IPongEngine<SocketChannel> getInstance() {
        if (instance == null) {
            instance = new PongEngine();
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
    public void sendCommand(AbstractServerCommand<IEngine<SocketChannel>, SocketChannel> command) throws IOException, NoEngineException {
        // todo temp code
        command.setEngine(this);
    }

    @Override
    public void sendFieldState(SocketChannel channel) throws IOException {
        receiveCommand(new ClientObjectCommand(field), channel);
    }
}
