package com.inkostilation.pong.server.network;

import com.inkostilation.pong.commands.AbstractPongCommand;
import com.inkostilation.pong.commands.AbstractServerCommand;
import com.inkostilation.pong.commands.ClientMessageCommand;
import com.inkostilation.pong.engine.IEngine;
import com.inkostilation.pong.engine.IPongEngine;
import com.inkostilation.pong.engine.NullEngine;
import com.inkostilation.pong.exceptions.NoEngineException;
import com.inkostilation.pong.server.engine.PongEngine;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class ServerCommandRouter implements ICommandRouter<SocketChannel> {

    @Override
    public void route(AbstractServerCommand<IEngine<SocketChannel>, SocketChannel> command) throws IOException, NoEngineException {
        if (command instanceof AbstractPongCommand) {
            PongEngine.getInstance().sendCommand(command);
        } else {
            command.setEngine(NullEngine.INSTANCE);
            command.execute();
            NetworkProcessor.getInstance().sendMessage(new ClientMessageCommand("pong"), command.getMarker());
        }
    }
}
