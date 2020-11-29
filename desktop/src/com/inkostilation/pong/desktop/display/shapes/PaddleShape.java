package com.inkostilation.pong.desktop.display.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inkostilation.pong.desktop.notification.ClientNotifier;
import com.inkostilation.pong.engine.Paddle;
import com.inkostilation.pong.engine.PlayerRole;
import com.inkostilation.pong.notifications.IObserver;

public class PaddleShape extends AbstractShape implements IObserver<Paddle> {

    private Paddle observable;
    private PlayerRole role;

    public PaddleShape(PlayerRole role) {
        ClientNotifier.getInstance().subscribe(this, Paddle.class);
         this.role = role;
    }

    @Override
    public Position draw(Position position, ShapeRenderer renderer) {
        renderer.setColor(Color.WHITE);
        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.rect(position.getX() + observable.getX(), position.getY() + observable.getY(),
                observable.getWidth(), observable.getHeight());
        return position;
    }

    @Override
    public void observe(Paddle observable) {
        if (observable != null && observable.getPlayerRole().equals(role)) {
            this.observable = observable;
            setReady(true);
        }
    }
}
