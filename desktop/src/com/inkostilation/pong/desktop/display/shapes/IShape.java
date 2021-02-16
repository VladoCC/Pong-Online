package com.inkostilation.pong.desktop.display.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

public interface IShape {

    DrawRect draw(DrawRect rect, ShapeRenderer renderer);

    void addChild(IShape shape);

    List<IShape> getChildren();

    DrawRect getChildrenRect();

    boolean isReady();

    default void drawShapeTree(ShapeRenderer renderer) {
        drawShapeTree(new DrawRect(new Vector2(0,0), new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())), renderer);
    }

    default DrawRect drawShapeTree(DrawRect rect, ShapeRenderer renderer) {
        if (isReady()) {
            DrawRect tmp = draw(rect, renderer);
            DrawRect childrenPos = getChildrenRect().getNewRect(rect.getBottomLeft().getX(), rect.getBottomLeft().getY());
            for (IShape shape : getChildren()) {
                childrenPos = shape.drawShapeTree(childrenPos, renderer);
            }
            return tmp;
        }
        return rect;
    }

    class Vector2 {
        private float x;
        private float y;

        public Vector2(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public void mult(float multplier) {
            x *= multplier;
            y *= multplier;
        }

        public Vector2 getNewPosition(float moveX, float moveY) {
            return new Vector2(x + moveX, y + moveY);
        }
    }

    class DrawRect {
        private Vector2 bottomLeft;
        private Vector2 topRight;

        public DrawRect(Vector2 bottomLeft, Vector2 topRight) {
            this.bottomLeft = bottomLeft;
            this.topRight = topRight;
        }

        public Vector2 getBottomLeft() {
            return bottomLeft;
        }

        public Vector2 getTopRight() {
            return topRight;
        }

        public DrawRect getNewRect(float moveX, float moveY) {
            return new DrawRect(bottomLeft.getNewPosition(moveX, moveY),
                    topRight.getNewPosition(moveX, moveY));
        }
    }
}
