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
    public void act(float delta) {
        List<PongGame> list = PongGame.getActiveGamesList();
        PongGame[] games = new PongGame[list.size()];
        games = list.toArray(games);
        Arrays.stream(games).forEach(g -> g.update(delta));
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
            switch (playersNumber) {
                case 0: {
                    player = PlayerRole.FIRST;
                    playersMap.get(channel).setPlayerRole(player);
                    playersMap.get(channel).getGame().setPlayerRole(player);
                    break;
                }
                case 1: {
                    if (!(playersMap.containsValue(PlayerRole.SECOND))) {
                        player = PlayerRole.SECOND;
                        playersMap.get(channel).setPlayerRole(player);
                        playersMap.get(channel).getGame().setPlayerRole(player);
                    } else {
                        player = PlayerRole.FIRST;
                        playersMap.get(channel).setPlayerRole(player);
                        playersMap.get(channel).getGame().setPlayerRole(player);
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
        switch (role) {
            case FIRST:
                paddle = playersMap.get(channel).getGame().getPaddle(PlayerRole.FIRST);
                break;
            case SECOND:
                paddle = playersMap.get(channel).getGame().getPaddle(PlayerRole.SECOND);
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
            PlayerRole role = playersMap.get(channel).getPlayerRole();
            switch (role) {
                case FIRST: {
                    playersMap.get(channel).getGame().setControlled(PlayerRole.FIRST, false);
                    break;
                }
                case SECOND:
                    playersMap.get(channel).getGame().setControlled(PlayerRole.SECOND, false);
                    break;
            }
            playersMap.get(channel).getGame().removePlayer();
            playersMap.remove(channel);
            receiveCommand(new ResponseMessageCommand("Exit success!"), channel);
        }
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
            PlayerRole role = playersMap.get(channel).getPlayerRole();
            switch (role) {
                case FIRST: {
                    playersMap.get(channel).getGame().setControlled(PlayerRole.FIRST, true);
                    break;
                }
                case SECOND:
                    playersMap.get(channel).getGame().setControlled(PlayerRole.SECOND, true);
                    break;
            }
        }
            if (playersMap.get(channel).getGame().isControlled(PlayerRole.FIRST) && playersMap.get(channel).getGame().isControlled(PlayerRole.SECOND)) {
                start(playersMap.get(channel).getGame());
            }
    }

    private void start(PongGame game) {
        game.start();
    }

}
