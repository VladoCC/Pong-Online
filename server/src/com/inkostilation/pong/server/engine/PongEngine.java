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
    private static int playersCount = 0;

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
    public void receiveCommand(AbstractResponseCommand command, SocketChannel channel) throws IOException {
        NetworkProcessor.getInstance().sendMessage(command, channel);
    }

    @Override
    public void sendCommand(AbstractRequestCommand<IEngine<SocketChannel>, SocketChannel> command) throws IOException, NoEngineException {
        // todo temp code
        command.setEngine(this);
    }

    @Override
    public void sendFieldState(SocketChannel channel) throws IOException {
        receiveCommand(new ResponseObjectCommand(field), channel);
    }

    @Override
    public void sendPlayerRole(SocketChannel channel) throws IOException {
        playersCount++;
        receiveCommand(new ResponsePlayerRoleCommand(playersCount), channel);
    }
}
