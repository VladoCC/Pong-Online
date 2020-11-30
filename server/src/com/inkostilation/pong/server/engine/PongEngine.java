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
    private Map<SocketChannel, PlayerRole> playersMap;

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
    public void sendCommand(AbstractRequestCommand<IEngine<SocketChannel>, SocketChannel>... commands) throws IOException, NoEngineException {
        // todo temp code
        for (AbstractRequestCommand<IEngine<SocketChannel>, SocketChannel> command: commands) {
            command.setEngine(this);
            command.execute();
        }
    }

    @Override
    public void sendFieldState(SocketChannel channel) throws IOException {
        receiveCommand(new ResponseFieldCommand(field), channel);
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
                field.getPaddle1().setPlayerRole(player);
                field.getPaddle1().setControlled(true);
                break;
            }
            case 1: {
                player = PlayerRole.SECOND;
                playersMap.put(channel, player);
                field.getPaddle2().setPlayerRole(player);
                field.getPaddle2().setControlled(true);
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

    @Override
    public void removePlayer(SocketChannel channel) throws IOException {
        if (playersMap.containsKey(channel)) {
            switch ((playersMap.get(channel).number)) {
                case 1: {
                    field.getPaddle1().setControlled(false);
                    break;
                }
                case 2: {
                    field.getPaddle2().setControlled(false);
                    break;
                }
            }
            playersMap.remove(channel);
        }
        receiveCommand(new ResponseMessageCommand("Exit success!"), channel);
    }
}
