package com.inkostilation.pong.engine;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.inkostilation.pong.commands.AbstractResponseCommand;
import com.inkostilation.pong.commands.response.ResponseGameStateCommand;

import java.util.*;

public class PongGame implements IUpdatable, ICommandSender {

    private Field field;
    private Score score;
    private GameState gameState;
    private int playersNumber;
    private PlayerRole winner;
    private boolean active = true;

    private boolean changed = true;

    private LinkedList<PlayerRole> roles = new LinkedList<>();

    private static ListMultimap<GameState, PongGame> activeGamesMap = ArrayListMultimap.create();

    public PongGame()
    {
        this.field = new Field();
        this.score = new Score();
        this.playersNumber = 0;
        this.gameState = GameState.WAITING;
        activeGamesMap.put(gameState, this);

        roles.add(PlayerRole.FIRST);
        roles.add(PlayerRole.SECOND);
        roles.add(PlayerRole.DENIED);
    }

    public static PongGame getWaitingGame(int index) {
        if (!activeGamesMap.isEmpty() && !getGameCollection(GameState.WAITING).isEmpty()) {
            PongGame tempGame = getGame(GameState.WAITING, index);
            return tempGame;
        } else {
            PongGame newGame = new PongGame();
            return newGame;
        }
    }

    public Field getField() {
        return field;
    }

    public Score getScore() {
        return score;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        activeGamesMap.remove(this.gameState, this);
        activeGamesMap.put(gameState, this);

        changed = true;
        this.gameState = gameState;
    }

    public PlayerRole addPlayer() {
        ++this.playersNumber;
        PlayerRole role = roles.size() > 1? roles.pop() : roles.peek();
        return role;
    }

    public void removePlayer(PlayerRole role) {
        --this.playersNumber;

        if (playersNumber == 0) {
            stop();
        }

        if (role != PlayerRole.DENIED) {
            roles.push(role);
            setGameState(GameState.INTERRUPTED);
        }
    }

    public void start() {
        //active = true;
        field.setStarted(true);

        setGameState(GameState.PLAYING);
    }
    
    public void stop() {
        activeGamesMap.remove(gameState, this);
        active = false;
    }

    public void scorePoint(PlayerRole role) {
        score.addPlayerScore(role, 1);
    }

    @Override
    public void update(float delta) {
        if (active) {
            if (score.getMaxValueCount() == 0) {
                field.update(delta);
                if (!field.isBallInBounds()) {
                    scorePoint(field.getBall().getX() < 0 ? PlayerRole.FIRST : PlayerRole.SECOND);
                    field.reset();
                    gameState = GameState.AFTER_GOAL_CONFIRMATION;
                }
            } else {
                winner = score.getMaxedPlayers().get(0);
                stop();
            }
        }
    }

    public static List<PongGame> getGameCollection(GameState state) {
        return activeGamesMap.get(state);
    }

    public static PongGame getGame(GameState state, int index) {
        return getGameCollection(state).get(index);
    }

    public static ListMultimap<GameState, PongGame> getActiveGamesMap() {
        return activeGamesMap;
    }

    public void setControlled(PlayerRole playerRole, boolean state) {
        getField().setControlled(playerRole, state);
    }

    public boolean isControlled(PlayerRole playerRole) {
        return getField().isControlled(playerRole);
    }

    public Paddle getPaddle(PlayerRole playerRole) {
        return getField().getPaddle(playerRole);
    }

    public void setPlayerRole(PlayerRole playerRole) {
        getField().getPaddle(playerRole).setPlayerRole(playerRole);
    }

    @Override
    public boolean hasCommands() {
        return changed || field.hasCommands() || score.hasCommands();
    }

    @Override
    public void getCommands(List<AbstractResponseCommand> pool) {
        if (changed) {
            changed = false;
            pool.add(new ResponseGameStateCommand(gameState));
        }

        field.getCommands(pool);
        score.getCommands(pool);
    }
}
