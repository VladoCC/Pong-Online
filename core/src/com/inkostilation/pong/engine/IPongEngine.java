package com.inkostilation.pong.engine;

import com.inkostilation.pong.commands.AbstractRequestCommand;
import com.inkostilation.pong.exceptions.NoEngineException;

import java.io.IOException;

public interface IPongEngine<M> extends IEngine<M> {
    void sendCommand(AbstractRequestCommand<IEngine<M>, M>... command) throws IOException, NoEngineException;
    void sendFieldState(M marker) throws IOException;
    void sendPlayerRole(M marker) throws IOException;
    void prepare(M marker) throws IOException;
    void applyInput(Direction direction, M marker);
    void create(M marker) throws IOException;
    void confirm(M Marker);
    void start(M marker);
}
