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
        float width = observable.getWidth();
        float height = observable.getHeight();
        renderer.setColor(Color.WHITE);
        renderer.rectLine(x, y + 2, x + width, y + 2, 4);
        renderer.rectLine(x, y + height - 2, x + width, y + height - 2, 4);
        renderer.rectLine(x + width / 2, y, x + width / 2, y + height / 4, 4);
        renderer.rectLine(x + width / 2, y + 3 * height / 4, x + width / 2, y + height, 4);
        renderer.circle(x + width / 2, y + height / 2, height / 4 + 2);
        renderer.setColor(Color.BLACK);
        renderer.circle(x + width / 2, y + height / 2, height / 4 - 2);
        renderer.setColor(Color.WHITE);
        renderer.circle(x + width / 2, y + height / 2,  4);
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
