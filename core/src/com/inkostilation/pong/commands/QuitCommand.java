package com.inkostilation.pong.commands;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class QuitCommand extends AbstractServerCommand<SocketChannel> {
    @Override
    public void execute() {
        try {
            getMarker().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
