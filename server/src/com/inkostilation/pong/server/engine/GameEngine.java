package com.inkostilation.pong.server.engine;

import com.inkostilation.pong.commands.*;
import com.inkostilation.pong.engine.Ball;
import com.inkostilation.pong.engine.IEngine;
import com.inkostilation.pong.engine.Paddle;
import com.inkostilation.pong.server.network.NetworkProcessor;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class GameEngine implements IEngine<SocketChannel> {

    private static IEngine<SocketChannel> instance = null;

    private Paddle paddle;
    private Ball ball;

    private GameEngine() {
        paddle = new Paddle(1);
        ball = new Ball();
    }

    public static IEngine<SocketChannel> getInstance() {
        if (instance == null) {
            instance = new GameEngine();
        }
        return instance;
    }

    @Override
    public void act() {
        paddle.move();
        ball.move();
        //ball.checkPaddleCollision();
    }

    @Override
    public void receiveCommand(AbstractClientCommand command, SocketChannel channel) throws IOException {
        NetworkProcessor.getInstance().sendMessage(command, channel);
    }

    @Override
    public void sendCommand(AbstractServerCommand<SocketChannel> command) throws IOException {
        // todo temp code
        command.execute();
        receiveCommand(new ClientMessageCommand("pong"), command.getMarker());
    }

    @Override
    public void updateField(SocketChannel channel) throws IOException {
        receiveCommand(new ClientObjectCommand(paddle), channel);
        receiveCommand(new ClientObjectCommand(ball), channel);
    }
}
