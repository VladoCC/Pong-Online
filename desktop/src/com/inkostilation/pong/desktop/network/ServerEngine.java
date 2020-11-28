package com.inkostilation.pong.desktop.network;

import com.inkostilation.pong.commands.AbstractClientCommand;
import com.inkostilation.pong.commands.AbstractServerCommand;
import com.inkostilation.pong.commands.ServerMessageCommand;
import com.inkostilation.pong.commands.UpdateCommand;
import com.inkostilation.pong.desktop.notification.ClientNotifier;
import com.inkostilation.pong.engine.IEngine;
import com.inkostilation.pong.exceptions.EmptyParcelException;
import com.inkostilation.pong.exceptions.NoEngineException;
import com.inkostilation.pong.exceptions.ParsingNotFinishedException;
import com.inkostilation.pong.processing.MessageParser;
import com.inkostilation.pong.processing.NetworkConnection;
import com.inkostilation.pong.processing.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServerEngine implements IEngine<Void> {

    private static final String host = "localhost";
    private static final int port = 8080;

    private SocketChannel channel;
    private Serializer serializer;

    ServerEngine() {
        try {
            channel = SocketChannel.open(new InetSocketAddress(host, port));
            serializer = new Serializer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void act() {
        try {
            sendCommand(new ServerMessageCommand("ping"));
            //sendCommand(new UpdateCommand());
            List<AbstractClientCommand> commands = listen();
            for (AbstractClientCommand command: commands) {
                receiveCommand(command, null);
            }
        } catch (IOException | NoEngineException e) {
            e.printStackTrace();
        }
    }

    private List<AbstractClientCommand> listen() throws IOException {
        List<String> objects = NetworkConnection.listen(channel);

        return objects.stream()
                .map(o -> {
                    AbstractClientCommand command = (AbstractClientCommand) serializer.deserialize(o);
                    command.setNotifier(ClientNotifier.getInstance());
                    return command;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void receiveCommand(AbstractClientCommand command, Void mark) throws IOException, NoEngineException {
        // todo temp code
        command.execute();
    }

    @Override
    public void sendCommand(AbstractServerCommand command) throws IOException {
        String msg = serializer.serialize(command);
        channel.write(ByteBuffer.wrap(msg.getBytes()));
    }
}
