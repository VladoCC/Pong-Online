package com.inkostilation.pong.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Rectangle implements IGeometricShape{

    private static final float FRICTION = 0.94f;

    private float x, y, yVel, width, height;

    public enum Direction {
        UP(-1), DOWN(1), IDLE(0);

        public final int value;

        Direction(int dir) {
            value = dir;
        }
    }

    private Direction accelerationDirection;

    public void setAccelerationDirection(Direction accelerationDirection) {
        this.accelerationDirection = accelerationDirection;
    }

    public Rectangle (float x, float y, float width, float height)
    {
        this.x = x;
        this.y = y;
        this.yVel = 0;
        this.width = width;
        this.height = height;
        accelerationDirection = Direction.IDLE;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void constrain(int fieldHeight)
    {
        if (y < 0) {
            y = 0;
        } else if (y > fieldHeight - height) {
            y = fieldHeight - height;
        }
    }

    @Override
    public boolean isInBounds(int fieldHeight) {
        if ((y < 0) || (y > fieldHeight - height))
            return false;
        return true;
    }

    @Override
    public void move() {
        int dir = accelerationDirection.value;
        if (dir != 0)
            yVel += 2 * dir;
        else
            yVel *= FRICTION;

        if (yVel >= 5)
            yVel = 5;
        else if (yVel <= -5)
            yVel = -5;

        y += yVel;
    }
}
