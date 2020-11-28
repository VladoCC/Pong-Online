package com.inkostilation.pong.desktop.display.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inkostilation.pong.desktop.notification.ClientNotifier;
import com.inkostilation.pong.engine.Field;
import com.inkostilation.pong.notifications.IObserver;

public class FieldShape extends AbstractShape implements IObserver<Field> {

    private Field observable;

    private float offsetX = 0, offsetY = 0;

    private final static int WIDTH = 3;

    public FieldShape() {
        ClientNotifier.getInstance().subscribe(this, Field.class);
    }

    @Override
    public Position draw(Position position, ShapeRenderer renderer) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        offsetX = (w - observable.getWidth() - position.getX()) / 2;
        offsetY = (h - observable.getHeight() - position.getY()) / 2;
        float x = position.getX() + offsetX;
        float y = position.getY() + offsetY;
        renderer.setColor(Color.WHITE);
        renderer.set(ShapeRenderer.ShapeType.Line);
        renderer.rectLine(x, y, x + observable.getWidth(), y + observable.getHeight(), WIDTH);
        return new Position(w, h);
    }

    @Override
    public void observe(Field observable) {
        this.observable = observable;
        setReady(true);
    }

    @Override
    public Position getChildrenOffset() {
        return new Position(offsetX, offsetY);
    }
}
