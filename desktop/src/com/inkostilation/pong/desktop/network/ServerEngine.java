package com.inkostilation.pong.desktop.network;

import com.inkostilation.pong.commands.AbstractResponseCommand;
import com.inkostilation.pong.commands.AbstractRequestCommand;
import com.inkostilation.pong.commands.RequestMessageCommand;
import com.inkostilation.pong.desktop.notification.ClientNotifier;
import com.inkostilation.pong.engine.IEngine;
import com.inkostilation.pong.exceptions.NoEngineException;
import com.inkostilation.pong.processing.NetworkConnection;
import com.inkostilation.pong.processing.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.stream.Collectors;

public class ServerEngine implements IEngine<Void> {

    private static final String host = "localhost";
    private static final int port = 8080;

    private SocketChannel channel;
    private Serializer serializer;

    private boolean connected = false;

    ServerEngine() {
        serializer = new Serializer();
    }

    @Override
    public void act() {
        try {
            if (!connected) {
                connect(host, port);
            } else {
                sendCommand(new RequestMessageCommand("ping"));
                //sendCommand(new UpdateCommand());
                List<AbstractResponseCommand> commands = listen();
                for (AbstractResponseCommand command : commands) {
                    receiveCommand(command, null);
                }
            }
        } catch (IOException | NoEngineException e) {
            e.printStackTrace();
        }
    }

    private void connect(String host, int port) {
        try {
            channel = SocketChannel.open(new InetSocketAddress(host, port));
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }
    }

    private List<AbstractResponseCommand> listen() throws IOException {
        List<String> objects = NetworkConnection.listen(channel);

        return objects.stream()
                .map(o -> {
                    AbstractResponseCommand command = (AbstractResponseCommand) serializer.deserialize(o);
                    command.setNotifier(ClientNotifier.getInstance());
                    return command;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void receiveCommand(AbstractResponseCommand command, Void mark) throws IOException, NoEngineException {
        // todo temp code
        command.execute();
    }

    @Override
    public void sendCommand(AbstractRequestCommand command) throws IOException {
        String msg = serializer.serialize(command);
        channel.write(ByteBuffer.wrap(msg.getBytes()));
    }
}
