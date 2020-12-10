package com.inkostilation.pong.desktop.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inkostilation.pong.commands.ExitGameCommand;
import com.inkostilation.pong.commands.RequestInputActionCommand;
import com.inkostilation.pong.commands.RequestReadinessCommand;
import com.inkostilation.pong.desktop.controls.InputSystem;
import com.inkostilation.pong.desktop.display.shapes.*;
import com.inkostilation.pong.desktop.network.Network;
import com.inkostilation.pong.engine.PlayerRole;
import com.inkostilation.pong.exceptions.NoEngineException;

import java.io.IOException;

public class PongScreen implements Screen {

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private IShape rootShape;

    @Override
    public void show() {
        rootShape = new ContainerShapes(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        rootShape.addChild(new ScoreShape());
        FieldShape field = new FieldShape();
        rootShape.addChild(field);
        field.addChild(new BallShape());
        field.addChild(new PaddleShape(PlayerRole.FIRST));
        field.addChild(new PaddleShape(PlayerRole.SECOND));

        Gdx.input.setInputProcessor(new InputSystem());
    }

    @Override
    public void render(float delta) {
        updateControls();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        rootShape.drawShapeTree(shapeRenderer);
        shapeRenderer.end();
        try {
            Network.getEngine().sendCommand(new RequestReadinessCommand());
        } catch (IOException | NoEngineException e) {
            e.printStackTrace();
        }
    }

    private void updateControls() {
        InputSystem system = (InputSystem) Gdx.input.getInputProcessor();
        if (system.isChanged()) {
            try {
                Network.getEngine().sendCommand(new RequestInputActionCommand(system.getDirection()));
            } catch (IOException | NoEngineException e) {
                e.printStackTrace();
            }
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
        try {
            Network.getEngine().sendCommand(new ExitGameCommand());
        } catch (IOException | NoEngineException e) {
            e.printStackTrace();
        }
    }
}
