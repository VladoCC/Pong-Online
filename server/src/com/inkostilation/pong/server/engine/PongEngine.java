package com.inkostilation.pong.server.engine;

import com.inkostilation.pong.commands.*;
import com.inkostilation.pong.engine.*;
import com.inkostilation.pong.exceptions.NoEngineException;
import com.inkostilation.pong.server.network.NetworkProcessor;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PongEngine implements IPongEngine<SocketChannel> {

    private static IPongEngine<SocketChannel> instance = null;

    private Field field;
    public Map<SocketChannel, PlayerRole> playersMap;

    private PongEngine() {
        playersMap = new HashMap<>();
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
    public void assignPlayerRole(SocketChannel channel) throws IOException {
        int playersNumber = playersMap.size();
        PlayerRole player;
        switch (playersNumber)
        {
            case 0: {
                player = PlayerRole.FIRST;
                playersMap.put(channel, player);
                break;
            }
            case 1: {
                player = PlayerRole.SECOND;
                playersMap.put(channel, player);
                break;
            }
            default: {
                player = PlayerRole.DENIED;
                playersMap.put(channel, player);
                break;
            }
        }
        receiveCommand(new ResponseObjectCommand(player), channel);
    }

    @Override
    public void sendPlayerRole(SocketChannel channel) throws IOException {
        if (playersMap.containsKey(channel)) {
            receiveCommand(new ResponsePlayerRoleCommand(playersMap.get(channel)), channel);
        }
    }
}
