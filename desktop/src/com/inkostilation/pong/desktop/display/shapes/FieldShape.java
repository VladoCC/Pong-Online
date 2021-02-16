package com.inkostilation.pong.desktop.display.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.inkostilation.pong.desktop.display.Colors;
import com.inkostilation.pong.desktop.notification.ClientNotifier;
import com.inkostilation.pong.engine.Ball;
import com.inkostilation.pong.engine.Field;
import com.inkostilation.pong.notifications.IObserver;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.PI;

public class FieldShape extends AbstractShape implements IObserver<Field> {

    private static final float GHOST_TIME = 0.1f;
    private static final float GHOST_SIZE = 70f;

    private Field observable;

    private float offsetX = 0, offsetY = 0;

    private final static int WIDTH = 3;

    private List<BallShape.Ghost> ghosts = new ArrayList<>();

    public FieldShape() {
        ClientNotifier.getInstance().subscribe(this, Field.class);
    }

    @Override
    public DrawRect draw(DrawRect rect, ShapeRenderer renderer) {
        float xEnd = rect.getTopRight().getX();
        float yEnd = rect.getTopRight().getY();

        float width = observable.getWidth();
        float height = observable.getHeight();

        offsetX = (xEnd - width - rect.getBottomLeft().getX()) / 2;
        offsetY = (yEnd - height - rect.getBottomLeft().getY()) / 2;

        float x = rect.getBottomLeft().getX() + offsetX;
        float y = rect.getBottomLeft().getY() + offsetY;

        for (BallShape.Ghost ghost : ghosts) {
            ghost.addTime(Gdx.graphics.getDeltaTime());
            float rad = GHOST_SIZE * (ghost.getTime() / GHOST_TIME);
            renderer.setColor(Colors.getOutlineColor());
            renderer.arc(x + ghost.getPos().getX(), y + ghost.getPos().getY(),
                    rad, ghost.getStart() * MathUtils.radiansToDegrees,180);
            renderer.setColor(Colors.getBackgroundColor());
            System.out.println((x + ghost.getPos().getX()) + " " + (y + ghost.getPos().getY()) + " " +
                    (rad - 2) + " " + ghost.getStart());
            renderer.arc(x + ghost.getPos().getX(), y + ghost.getPos().getY(),
                    rad - 2,ghost.getStart() * MathUtils.radiansToDegrees,180);
        }

        ghosts.removeIf(g -> g.getTime() > GHOST_TIME);

        renderer.setColor(Colors.getOutlineColor());
        renderer.rectLine(x, y + 2, x + width, y + 2, 6);
        renderer.rectLine(x, y + height - 2, x + width, y + height - 2, 6);
        renderer.rectLine(x + width / 2, y, x + width / 2, y + height / 4, 6);
        renderer.rectLine(x + width / 2, y + 3 * height / 4, x + width / 2, y + height, 6);

        renderer.setColor(Colors.getDarkColor());
        renderer.rectLine(x, y + 2, x + width, y + 2, 4);
        renderer.rectLine(x, y + height - 2, x + width, y + height - 2, 4);
        renderer.rectLine(x + width / 2, y, x + width / 2, y + height / 4, 4);
        renderer.rectLine(x + width / 2, y + 3 * height / 4, x + width / 2, y + height, 4);
        renderer.setColor(Colors.getOutlineColor());
        renderer.circle(x + width / 2, y + height / 2, height / 4 + 3);
        renderer.setColor(Colors.getDarkColor());
        renderer.circle(x + width / 2, y + height / 2, height / 4 + 2);
        renderer.setColor(Colors.getOutlineColor());
        renderer.circle(x + width / 2, y + height / 2, height / 4 - 2);
        renderer.setColor(Colors.getBackgroundColor());
        renderer.circle(x + width / 2, y + height / 2, height / 4 - 3);
        renderer.setColor(Colors.getOutlineColor());
        renderer.circle(x + width / 2, y + height / 2,  5);
        renderer.setColor(Colors.getDarkColor());
        renderer.circle(x + width / 2, y + height / 2,  4);

        return new DrawRect(new Vector2(rect.getBottomLeft().getX(), yEnd), rect.getTopRight().getNewPosition(0, 0));
    }

    @Override
    public void observe(Field... observable) {
        this.observable = observable[0];
        setReady(true);
    }

    @Override
    public DrawRect getChildrenRect() {
        return new DrawRect(new Vector2(offsetX, offsetY), new Vector2(offsetX + observable.getWidth(), offsetY + observable.getHeight()));
    }

    public void addGhost(BallShape.Ghost ghost) {
        ghosts.add(ghost);
    }
}
