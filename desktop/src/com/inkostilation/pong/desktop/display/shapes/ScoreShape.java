package com.inkostilation.pong.desktop.display.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inkostilation.pong.desktop.display.Colors;
import com.inkostilation.pong.desktop.notification.ClientNotifier;
import com.inkostilation.pong.engine.PlayerRole;
import com.inkostilation.pong.engine.Score;
import com.inkostilation.pong.notifications.IObserver;

public class ScoreShape extends AbstractShape implements IObserver<Score> {

    private static final float height = 100;
    private static final float pointDim = 20;

    private Score observable;

    public ScoreShape() {
        ClientNotifier.getInstance().subscribe(this, Score.class);
    }

    @Override
    public DrawRect draw(DrawRect rect, ShapeRenderer renderer) {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        renderer.set(ShapeRenderer.ShapeType.Filled);

        renderer.setColor(Colors.getOutlineColor());
        renderer.rect(0, h - height - 1, w, 10);
        renderer.rect(0, h - 9, w, 9);
        renderer.rect(w / 2 - 5, h - height + 8, 10, height - 8);

        renderer.setColor(Colors.getDarkColor());
        renderer.rect(0, h - height, w, 8);
        renderer.rect(0, h - 8, w, 8);
        renderer.rect(w / 2 - 4, h - height, 8, height);

        int count = observable.getMaxScoreValue();
        drawScore(observable.getPlayerScore(PlayerRole.FIRST), count, new Vector2(w / 12, h - height / 2 - pointDim / 2), w / 2 - w / 6, renderer, false);

        drawScore(observable.getPlayerScore(PlayerRole.SECOND), count, new Vector2( w / 2 + w / 12, h - height / 2 - pointDim / 2), w / 2 - w / 6, renderer, true);

        return new DrawRect(rect.getBottomLeft(), rect.getTopRight().getNewPosition(0, -height));
    }

    private void drawScore(int score, int maxScore, Vector2 startPos, float width, ShapeRenderer renderer, boolean right) {
        float step = (width - pointDim) / (maxScore - 1);
        for (int i = 0; i < maxScore; i++) {
            renderer.setColor(Colors.getOutlineColor());
            renderer.rect(startPos.getX() + step * i - 1, startPos.getY() - 1, pointDim + 2, pointDim + 2);
            if ((i < score && !right) || (right && i >= observable.getMaxScoreValue() - score)) {
                renderer.setColor(Colors.getLightColor());
            } else {
                renderer.setColor(Colors.getDarkColor());
            }
            renderer.rect(startPos.getX() + step * i, startPos.getY(), pointDim, pointDim);
        }
    }

    @Override
    public void observe(Score... observable) {
        System.out.println("Score observed");
        if (observable != null) {
            this.observable = observable[0];
            setReady(true);
        }
    }
}
