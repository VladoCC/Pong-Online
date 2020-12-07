package com.inkostilation.pong.server.engine;

import com.inkostilation.pong.commands.*;
import com.inkostilation.pong.engine.*;
import com.inkostilation.pong.exceptions.NoEngineException;
import com.inkostilation.pong.server.network.NetworkProcessor;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.*;

public class PongEngine implements IPongEngine<SocketChannel> {

    private static IPongEngine<SocketChannel> instance = null;

    private Map<SocketChannel, PlayerData> playersMap;

    private PongEngine() {
        playersMap = new HashMap<>();
    }

    public static IPongEngine<SocketChannel> getInstance() {
        if (instance == null) {
            instance = new PongEngine();
        }
        return instance;
    }

    @Override
    public void act() {
        List<PongGame> list = PongGame.getActiveGamesList();
        PongGame[] games = new PongGame[list.size()];
        games = list.toArray(games);
        Arrays.stream(games).forEach(PongGame::update);
    }

    @Override
    public void receiveCommand(AbstractResponseCommand command, SocketChannel channel) throws IOException {
        NetworkProcessor.getInstance().sendMessage(command, channel);
    }

    @Override
    public void sendCommand(AbstractRequestCommand<IEngine<SocketChannel>, SocketChannel>... commands) throws IOException, NoEngineException {
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
        Field field = playersMap.get(channel).getGame().getField();
        receiveCommand(new ResponseFieldCommand(field), channel);
    }

    @Override
    public void prepare(SocketChannel channel) throws IOException {
        if (playersMap.containsKey(channel)) {
            int playersNumber = playersMap.size() - 1;
            PlayerRole player;
            Field field = playersMap.get(channel).getGame().getField();
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
    public void applyInput(Direction direction, SocketChannel channel) {
        PlayerRole role = playersMap.get(channel).getPlayerRole();
        Paddle paddle = null;
        boolean allowed = true;
        Field field = playersMap.get(channel).getGame().getField();
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
        } else {
            receiveCommand(new ResponsePlayerRoleCommand(PlayerRole.DENIED), channel);
        }
    }

    @Override
    public void sendScore(SocketChannel channel) throws IOException {
        receiveCommand(new ResponseScoreCommand(playersMap.get(channel).getGame().getScore()), channel);
    }

    private void removePlayer(SocketChannel channel) throws IOException {
        if (playersMap.containsKey(channel)) {
            Field field = playersMap.get(channel).getGame().getField();
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
            playersMap.get(channel).getGame().removePlayer();
            playersMap.remove(channel);
        }
        receiveCommand(new ResponseMessageCommand("Exit success!"), channel);
    }

    @Override
    public void connectToGame(SocketChannel channel) throws IOException {
        if (!playersMap.containsKey(channel)) {
            PongGame game = PongGame.getWaitingGame();
            game.addPlayer();
            playersMap.put(channel, new PlayerData(game, null));
        }
        else
            receiveCommand(new ResponseMessageCommand("You are playing a game!"), channel);
    }

    @Override
    public void confirm(SocketChannel channel) {
        if (playersMap.containsKey(channel)) {
            Field field = playersMap.get(channel).getGame().getField();

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

            // TODO make adequate check for readiness of both players
            if (field.getPaddle1().isControlled() && field.getPaddle2().isControlled()) {
                start(playersMap.get(channel).getGame());
            }
        }
    }

    private void start(PongGame game) {
        game.start();
    }
}
