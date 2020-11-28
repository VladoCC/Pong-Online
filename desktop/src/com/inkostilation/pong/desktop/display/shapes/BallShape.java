package com.inkostilation.pong.desktop.display.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inkostilation.pong.desktop.notification.ClientNotifier;
import com.inkostilation.pong.engine.Ball;
import com.inkostilation.pong.notifications.IObserver;

public class BallShape extends AbstractShape implements IObserver<Ball> {

    private Ball observable;

    public BallShape() {
        ClientNotifier.getInstance().subscribe(this, Ball.class);
    }

    @Override
    public Position draw(Position position, ShapeRenderer renderer) {
        float radius = observable.getRadius();
        float x = observable.getX() + position.getX();
        float y = observable.getY() + position.getY();
        renderer.setColor(Color.WHITE);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.circle(x, y, radius);
        return position;
    }

    @Override
    public void observe(Ball observable) {
        this.observable = observable;
        setReady(true);
    }
}
