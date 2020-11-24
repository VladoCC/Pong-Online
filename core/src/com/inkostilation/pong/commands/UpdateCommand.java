package com.inkostilation.pong.commands;

import com.inkostilation.pong.engine.IEngine;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class UpdateCommand extends AbstractServerCommand<SocketChannel> {
    @Override
    public void execute() throws IOException {
       getEngine().updateField(getMarker());
    }
}