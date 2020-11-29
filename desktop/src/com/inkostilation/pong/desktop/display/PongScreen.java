package com.inkostilation.pong.desktop.display;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inkostilation.pong.desktop.display.shapes.*;
import com.inkostilation.pong.engine.PlayerRole;

public class PongScreen implements Screen {

    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private IShape rootShape;

    @Override
    public void show() {
        rootShape = new ContainerShapes(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        FieldShape field = new FieldShape();
        rootShape.addChild(field);
        field.addChild(new BallShape());
        field.addChild(new PaddleShape(PlayerRole.FIRST));
        field.addChild(new PaddleShape(PlayerRole.SECOND));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        rootShape.drawShapeTree(new IShape.Position(0, 0), shapeRenderer);
        shapeRenderer.end();
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

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
