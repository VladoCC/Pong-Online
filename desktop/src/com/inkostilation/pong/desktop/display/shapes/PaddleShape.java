package com.inkostilation.pong.desktop.display.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inkostilation.pong.desktop.display.Colors;
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
    public DrawRect draw(DrawRect rect, ShapeRenderer renderer) {
        renderer.set(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(Colors.getOutlineColor());
        renderer.rect(observable.getX() + rect.getBottomLeft().getX() + observable.getWidth() * 3 / 8 - 1, rect.getBottomLeft().getY() + 4,
                observable.getWidth() / 4 + 2, rect.getTopRight().getY() - rect.getBottomLeft().getY() - 8);
        renderer.setColor(Colors.getDarkColor());
        renderer.rect(observable.getX() + rect.getBottomLeft().getX() + observable.getWidth() * 3 / 8, rect.getBottomLeft().getY() + 3,
                observable.getWidth() / 4, rect.getTopRight().getY() - rect.getBottomLeft().getY() - 6);

        renderer.setColor(Colors.getOutlineColor());
        renderer.rect(rect.getBottomLeft().getX() + observable.getX() - 1, rect.getBottomLeft().getY() + observable.getY() - 1,
                observable.getWidth() + 2, observable.getHeight() + 2);

        if (observable.isControlled()) {
            renderer.setColor(Colors.getLightColor());
        } else {
            renderer.setColor(Colors.getDarkColor());
        }
        renderer.rect(rect.getBottomLeft().getX() + observable.getX(), rect.getBottomLeft().getY() + observable.getY(),
                observable.getWidth(), observable.getHeight());

        return rect;
    }

    @Override
    public void observe(Paddle... observable) {
        Paddle first = observable[0];
        if (first.getPlayerRole() != null && first.getPlayerRole().equals(role)) {
            this.observable = first;
            setReady(true);
        }
    }
}
