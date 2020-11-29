package com.inkostilation.pong.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle extends Rectangle {

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
    private PlayerRole playerNumber;

    public Paddle(float x, float y) {
        accelerationDirection = Direction.IDLE;
        /*switch (player)
        {
            case 1: {
                this.playerNumber = PlayerRole.FIRST;
                break;
            }
            case 2: {
                this.playerNumber = PlayerRole.SECOND;
                break;
            }
        }*/
        this.x = x;
        this.y = y;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.rect(x, y, getWidth(), getHeight());
    }

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

    public boolean isInBounds(int height) {
        if ((y < 0) || (y > height - getHeight()))
            return false;
        return true;
    }

    public void constrain(int height)
    {
        if (y < 0) {
            y = 0;
        } else if (y > height - getHeight()) {
            y = height - getHeight();
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

    public PlayerRole getPlayerNumber() {
        return playerNumber;
    }
}
