package com.inkostilation.pong.server.network;

import com.inkostilation.pong.commands.AbstractResponseCommand;
import com.inkostilation.pong.commands.AbstractRequestCommand;
import com.inkostilation.pong.engine.IEngine;
import com.inkostilation.pong.processing.NetworkConnection;
import com.inkostilation.pong.processing.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NetworkProcessor implements IProcessor {

    private static final String host = "localhost";
    private static final int port = 8080;

    private static NetworkProcessor instance = null;

    private Selector selector;
    private ServerSocketChannel serverSocket;
    private Serializer serializer = new Serializer();
    private ICommandRouter<SocketChannel> router = new ServerCommandRouter();

    private boolean started = true;

    private NetworkProcessor() {
        try {
            selector = Selector.open();
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(host, port));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static NetworkProcessor getInstance() {
        if (instance == null) {
            instance = new NetworkProcessor();
        }
        return instance;
    }

    @Override
    public void start() {
        started = true;
    }

    @Override
    public void stop() {
        started = false;
    }

    @Override
    public void processConnections() {
        try {
            if (started) {
                selector.select();
                final Set<SelectionKey> selectedKeys = selector.selectedKeys();
                final Iterator<SelectionKey> iter = selectedKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isAcceptable()) {
                        register();
                    }
                    if (key.isReadable()) {
                        receiveMessage(key);
                    }
                    iter.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void register() throws IOException {
        SocketChannel client  = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    private void receiveMessage(SelectionKey key) throws IOException {
        List<String> objects = parseObjects((SocketChannel) key.channel());
        List<AbstractRequestCommand<IEngine<SocketChannel>, SocketChannel>> commands = objects.stream()
                .map(o -> {
                    AbstractRequestCommand<IEngine<SocketChannel>, SocketChannel> command = (AbstractRequestCommand<IEngine<SocketChannel>, SocketChannel>) serializer.deserialize(o);
                    command.setMarker((SocketChannel) key.channel());
                    return command;
                })
                .collect(Collectors.toList());

        for (AbstractRequestCommand<IEngine<SocketChannel>, SocketChannel> command: commands) {
            try {
                router.route(command);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private List<String> parseObjects(SocketChannel channel) throws IOException {
        return NetworkConnection.listen(channel);
    }

    @Override
    public void sendMessage(AbstractResponseCommand command, SocketChannel channel) {
        String message = serializer.serialize(command);
        try {
            channel.write(ByteBuffer.wrap(message.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isStarted() {
        return started;
    }
}
