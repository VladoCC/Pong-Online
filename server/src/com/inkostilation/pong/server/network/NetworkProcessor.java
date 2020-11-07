package com.inkostilation.pong.server.network;

import com.inkostilation.pong.commands.IClientCommand;
import com.inkostilation.pong.commands.IServerCommand;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class NetworkProcessor implements IProcessor {

    private static final String host = "localhost";
    private static final int port = 8080;
    private static final int bufferSize = 1024;

    private Selector selector;
    private ServerSocketChannel serverSocket;

    private boolean started = true;

    private ByteBuffer buffer = ByteBuffer.allocate(bufferSize);

    public NetworkProcessor() {
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

    @Override
    public void start() {
        started = true;
    }

    @Override
    public void stop() {
        started = false;
    }

    public void processConnectons() {
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
        SocketChannel client = (SocketChannel) key.channel();
        String str = "";
        ArrayList<String> list = new  ArrayList<>();

        /** message parsing */
        while (true) {
            try {
                client.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
                client.close();
            }
            str += new String(buffer.array()).trim();
            int counter = 0;
            int parcelStart = -1;
            int parcelEnd;
            for (int i = 0; i < str.length(); i++) {
                char elem = str.charAt(i);
                if (elem == '{') {
                    if (counter == 0) {
                        parcelStart = i;
                    }
                    counter++;
                }
                if (elem == '}' && counter > 0)  { //check counter to see if we are in json or not
                    counter--;
                    if (counter == 0 && parcelStart != -1) {
                        parcelEnd = i;
                        list.add(str.substring(parcelStart, parcelEnd + 1));
                    }
                }
            }
            buffer.clear();

            //todo temp code for echo
            list.add(str);

            if (counter == 0) {
                break;
            }
        }

        /** message processing (echo for now)*/
        for (String message: list) {
            try {
                String result;
                //result = processConnection(gson.fromJson(message))
                System.out.println(message);
                result = message;
                buffer.flip();
                client.write(ByteBuffer.wrap(message.getBytes()));
                buffer.clear();
                if (message.equals("quit")) {
                    client.close();
                    list.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public IClientCommand processConnection(IServerCommand command) {
        return null;
    }

    public boolean isStarted() {
        return started;
    }
}
