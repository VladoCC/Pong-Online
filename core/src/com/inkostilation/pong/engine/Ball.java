package com.inkostilation.pong.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

public class Ball {
    private float xVel, yVel, x, y;

    public Ball() {
        x = 350;
        y = 250;
        xVel = getRandomSpeed() * getRandomDirection();
        yVel = getRandomSpeed() * getRandomDirection();
    }

    private float getRandomSpeed()
    {
        return new Random().nextFloat() * 3 + 2;
    }

    private int getRandomDirection() {
        if (new Random().nextBoolean()) {
            return 1;
        } else {
            return -1;
        }
    }

    public boolean isColliding(Paddle paddle) {
        if (x <= paddle.getX()) {
            return y >= paddle.getY() && y <= paddle.getY() + 80;
        }
        return false;
    }

    public void applyCollision() {
        xVel = -xVel;
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(x - 10, y - 10, 20);
    }

    public void move() {
        x += xVel;
        y += yVel;

        if (y < 10) {
            yVel = -yVel;
        }
        if (y > 490) {
            yVel = -yVel;
        }
    }
}
