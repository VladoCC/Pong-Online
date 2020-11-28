package com.inkostilation.pong.desktop.display.shapes;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

public interface IShape {

    Position draw(Position position, ShapeRenderer renderer);

    void addChild(IShape shape);

    List<IShape> getChildren();

    Position getChildrenOffset();

    boolean isReady();

    default Position drawShapeTree(Position pos, ShapeRenderer renderer) {
        if (isReady()) {
            Position tmp = draw(pos, renderer);
            Position childrenPos = getChildrenOffset().getNewPosition(pos.getX(), pos.getY());
            for (IShape shape : getChildren()) {
                childrenPos = shape.drawShapeTree(childrenPos, renderer);
            }
            return tmp;
        }
        return pos;
    }

    class Position {
        private float x;
        private float y;

        public Position(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public Position getNewPosition(float moveX, float moveY) {
            return new Position(x + moveX, y + moveY);
        }
    }
}
