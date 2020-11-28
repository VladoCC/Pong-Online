package com.inkostilation.pong.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle {

    private static final float FRICTION = 0.94f;
    public enum Direction {
        UP(-1), DOWN(1), IDLE(0);

        public final int value;

        Direction(int dir) {
            value = dir;
        }
    }

    private float x, y, yVel = 0;
    private Direction accelerationDirection;

    public Paddle(float x, float y) {
        accelerationDirection = Direction.IDLE;

        this.x = x;
        this.y = y;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x, y, 20, 80);
    }

    public void move() {
        int dir = accelerationDirection.value;
        if (dir != 0) {
            yVel += 2 * dir;
        } else {
            yVel *= FRICTION;
        }

        if (yVel >= 5) {
            yVel = 5;
        } else if (yVel <= -5) {
            yVel = -5;
        }

        y += yVel;

        if (y < 0) {
            y = 0;
        } else if (y > 420) {
            y = 420;
        }
    }

    public void setAccelerationDirection(Direction accelerationDirection) {
        this.accelerationDirection = accelerationDirection;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
