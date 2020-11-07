package com.inkostilation.pong.desktop.network;

import com.inkostilation.pong.commands.IClientCommand;
import com.inkostilation.pong.commands.IServerCommand;
import com.inkostilation.pong.engine.IEngine;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ServerEngine implements IEngine {

    private static final String host = "localhost";
    private static final int port = 8080;
    private static final int bufferSize = 1024;

    private SocketChannel client;
    private ByteBuffer buffer;

    ServerEngine() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 8080));
            buffer = ByteBuffer.allocate(bufferSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void act() {
        try {
            sendCommand(null);
            IClientCommand response = receiveCommand();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IClientCommand receiveCommand() throws IOException {
        client.read(buffer);
        String response = new String(buffer.array()).trim();
        System.out.println(response);
        return null;
    }

    @Override
    public void sendCommand(IServerCommand command) throws IOException {
        String msg = "pong";
        buffer = ByteBuffer.wrap(msg.getBytes());
        client.write(buffer);
        buffer.clear();
    }
}
