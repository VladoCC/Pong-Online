package com.inkostilation.pong.desktop.display;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.inkostilation.pong.commands.ExitGameCommand;
import com.inkostilation.pong.commands.request.RequestInputActionCommand;
import com.inkostilation.pong.commands.request.RequestReadinessCommand;
import com.inkostilation.pong.commands.request.RequestUpdateCommand;
import com.inkostilation.pong.desktop.controls.InputSystem;
import com.inkostilation.pong.desktop.display.shapes.*;
import com.inkostilation.pong.desktop.network.Network;
import com.inkostilation.pong.desktop.notification.ClientNotifier;
import com.inkostilation.pong.engine.GameState;
import com.inkostilation.pong.engine.PlayerRole;
import com.inkostilation.pong.exceptions.NoEngineException;
import com.inkostilation.pong.notifications.IObserver;

import java.io.IOException;

public class PongScreen implements Screen, IObserver<GameState> {

    private ShapeRenderer shapeRenderer = new PongRenderer();
    private IShape rootShape;

    private GameState state;
    private boolean ready = false;

    private BallShape ballShape;
    private FieldShape field;
    private ImplosionShape implosiomShape;
    private ExplosionShape explosionShape;

    private Matrix4 matrix;

    @Override
    public void show() {
        ClientNotifier.getInstance().subscribe(this, GameState.class);

        rootShape = new ContainerShapes(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        rootShape.addChild(new ScoreShape());
        field = new FieldShape();
        rootShape.addChild(field);

        implosiomShape = new ImplosionShape();
        field.addChild(implosiomShape);

        explosionShape = new ExplosionShape();
        field.addChild(explosionShape);

        field.addChild(new PaddleShape(PlayerRole.FIRST));
        field.addChild(new PaddleShape(PlayerRole.SECOND));

        ballShape = new BallShape(field);
        field.addChild(ballShape);

        matrix = shapeRenderer.getTransformMatrix();
        Gdx.input.setInputProcessor(new InputSystem());
    }

    @Override
    public void render(float delta) {
        Color color = Colors.getBackgroundColor();
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        rootShape.drawShapeTree(shapeRenderer);
        shapeRenderer.end();

        try {
            InputSystem system = (InputSystem) Gdx.input.getInputProcessor();
            if (system.isChanged()) {
                if (!ready) {
                    ready = true;
                    Network.getEngine().sendCommand(new RequestReadinessCommand());
                } else {
                    Network.getEngine().sendCommand(new RequestInputActionCommand(system.getDirection()));
                }
            } else {
                Network.getEngine().sendCommand(new RequestUpdateCommand());
            }
        } catch (IOException | NoEngineException e) {
            e.printStackTrace();
        }

        if (state == GameState.INACTIVE) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new LobbyScreen());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        ClientNotifier.getInstance().unsubscribe(this, GameState.class);
        try {
            Network.getEngine().sendCommand(new ExitGameCommand());
        } catch (IOException | NoEngineException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void observe(GameState... observable) {
        state = observable[0];

        if (state == GameState.WAITING) {
            ready = false;
            ballShape.setVisible(true);
            implosiomShape.setWorking(false);
        } else if (state == GameState.AFTER_GOAL_CONFIRMATION) {
            explosionShape.start(ballShape.getPosition(), ballShape.getVelocity(), 2f);
            ballShape.setVisible(false);
            implosiomShape.setWorking(true);
        }
    }
}
