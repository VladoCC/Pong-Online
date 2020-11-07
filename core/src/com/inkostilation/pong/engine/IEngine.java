package com.inkostilation.pong.engine;

import com.inkostilation.pong.commands.IClientCommand;
import com.inkostilation.pong.commands.IServerCommand;

import java.io.IOException;

public interface IEngine {

    void act();
    IClientCommand receiveCommand() throws IOException;
    void sendCommand(IServerCommand command) throws IOException;
}
