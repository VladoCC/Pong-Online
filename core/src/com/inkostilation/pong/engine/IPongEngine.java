package com.inkostilation.pong.engine;

import com.inkostilation.pong.commands.AbstractRequestCommand;
import com.inkostilation.pong.exceptions.NoEngineException;

import java.io.IOException;

public interface IPongEngine<M> extends IEngine<M> {
    void sendCommand(AbstractRequestCommand<IEngine<M>, M>... command) throws IOException, NoEngineException;
    void sendFieldState(M marker) throws IOException;
    void sendPlayerRole(M marker) throws IOException;
    void assignPlayerRole(M marker) throws IOException;
    void applyInput(Direction direction, M marker);
    void startGame(M marker) throws IOException;
    void confirmReadiness(M Marker) throws IOException;
}
