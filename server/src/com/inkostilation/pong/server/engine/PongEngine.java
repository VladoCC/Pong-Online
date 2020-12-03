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
    private Map<SocketChannel, PlayerData> playersMap;

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
        for (AbstractRequestCommand<IEngine<SocketChannel>, SocketChannel> command : commands) {
            command.setEngine(this);
            command.execute();
        }
    }

    @Override
    public void quit(SocketChannel channel) throws IOException {
        removePlayer(channel);
    }

    @Override
    public void sendFieldState(SocketChannel channel) throws IOException {
        receiveCommand(new ResponseFieldCommand(field), channel);
    }

    @Override
    public void prepare(SocketChannel channel) throws IOException {
        if (playersMap.containsKey(channel)) {
            int playersNumber = playersMap.size();
            PlayerRole player;
            switch (playersNumber) {
                case 0: {
                    player = PlayerRole.FIRST;
                    playersMap.get(channel).setPlayerRole(player);
                    field.getPaddle1().setPlayerRole(player);
                    break;
                }
                case 1: {
                    if (!(playersMap.containsValue(PlayerRole.SECOND))) {
                        player = PlayerRole.SECOND;
                        playersMap.get(channel).setPlayerRole(player);
                        field.getPaddle2().setPlayerRole(player);
                    } else {
                        player = PlayerRole.FIRST;
                        playersMap.get(channel).setPlayerRole(player);
                        field.getPaddle1().setPlayerRole(player);
                    }
                    break;
                }
                default: {
                    player = PlayerRole.DENIED;
                    playersMap.get(channel).setPlayerRole(player);
                    break;
                }
            }
            receiveCommand(new ResponseObjectCommand(player), channel);
        }
    }

    @Override
    public void applyInput(Direction direction, SocketChannel marker) {
        PlayerRole role = playersMap.get(marker).getPlayerRole();
        Paddle paddle = null;
        boolean allowed = true;
        switch (role) {
            case FIRST:
                paddle = field.getPaddle1();
                break;
            case SECOND:
                paddle = field.getPaddle2();
                break;
            default:
                allowed = false;
        }
        if (allowed) {
            paddle.setAccelerationDirection(direction);
        }
    }

    @Override
    public void sendPlayerRole(SocketChannel channel) throws IOException {
        if (playersMap.containsKey(channel)) {
            receiveCommand(new ResponsePlayerRoleCommand(playersMap.get(channel).getPlayerRole()), channel);
        }
    }

    private void removePlayer(SocketChannel channel) throws IOException {
        if (playersMap.containsKey(channel)) {
            switch ((playersMap.get(channel)).getPlayerRole()) {
                case FIRST: {
                    field.getPaddle1().setControlled(false);
                    break;
                }
                case SECOND: {
                    field.getPaddle2().setControlled(false);
                    break;
                }
            }
            playersMap.remove(channel);
        }
        receiveCommand(new ResponseMessageCommand("Exit success!"), channel);
    }

    @Override
    public void create(SocketChannel channel) throws IOException {
        if (!playersMap.containsKey(channel)) {
            Game newGame = new Game();
            playersMap.put(channel, new PlayerData(newGame, null));
        }
        else
            receiveCommand(new ResponseMessageCommand("You are playing a game!"), channel);
    }

    @Override
    public void confirm(SocketChannel channel) {
        if (playersMap.containsKey(channel)) {
            switch ((playersMap.get(channel)).getPlayerRole()) {
                case FIRST: {
                    field.getPaddle1().setControlled(true);
                    break;
                }
                case SECOND: {
                    field.getPaddle2().setControlled(true);
                    break;
                }
            }
        }
    }

    @Override
    public void start(SocketChannel channel) {
        if (playersMap.containsKey(channel))
            playersMap.get(channel).getGame().start();
    }
}
