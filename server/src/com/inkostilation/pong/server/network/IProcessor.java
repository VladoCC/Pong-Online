package com.inkostilation.pong.server.network;

import com.inkostilation.pong.commands.IClientCommand;
import com.inkostilation.pong.commands.IServerCommand;

public interface IProcessor {

    void start();
    void stop();
    IClientCommand processConnection(IServerCommand command);
}
