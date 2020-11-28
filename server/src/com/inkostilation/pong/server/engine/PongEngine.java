package com.inkostilation.pong.server.engine;

import com.inkostilation.pong.commands.*;
import com.inkostilation.pong.engine.Ball;
import com.inkostilation.pong.engine.IEngine;
import com.inkostilation.pong.engine.IPongEngine;
import com.inkostilation.pong.engine.Paddle;
import com.inkostilation.pong.exceptions.NoEngineException;
import com.inkostilation.pong.server.network.NetworkProcessor;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class PongEngine implements IPongEngine<SocketChannel> {

    private static IPongEngine<SocketChannel> instance = null;

    private Paddle paddle;
    private Ball ball;

    private PongEngine() {
        paddle = new Paddle(50, 250);
        ball = new Ball();
    }

    public static IPongEngine<SocketChannel> getInstance() {
        if (instance == null) {
            instance = new PongEngine();
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
    public void sendCommand(AbstractServerCommand<IEngine<SocketChannel>, SocketChannel> command) throws IOException, NoEngineException {
        // todo temp code
        command.setEngine(this);

    }

    @Override
    public void sendFieldState(SocketChannel channel) throws IOException {
        receiveCommand(new ClientObjectCommand(paddle), channel);
        receiveCommand(new ClientObjectCommand(ball), channel);
    }
}
