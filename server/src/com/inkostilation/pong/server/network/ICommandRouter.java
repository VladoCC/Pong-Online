package com.inkostilation.pong.server.network;

import com.inkostilation.pong.commands.AbstractServerCommand;
import com.inkostilation.pong.engine.IEngine;
import com.inkostilation.pong.exceptions.NoEngineException;

import java.io.IOException;

public interface ICommandRouter<M> {

    void route(AbstractServerCommand<IEngine<M>, M> command) throws IOException, NoEngineException;
}
