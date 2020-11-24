package com.inkostilation.pong.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Ball {
    float xVel, yVel, x, y;

    public Ball() {
        x = 350;
        y = 250;
        xVel = (float) (getRandomSpeed() * getRandomDirection());
        yVel = (float) (getRandomSpeed() * getRandomDirection());
    }

    public double getRandomSpeed()
    {
        return (Math.random() * 3 + 2);
    }

    public int getRandomDirection() {
        int rand = (int) (Math.random() * 2);
        if (rand == 1) {
            return 1;
        } else {
            return -1;
        }
    }

    public void checkPaddleCollision(Paddle p1, Paddle p2) {
        if (x <= 50) {
            if (y >= p1.y && y <= p1.y + 80) {
                xVel = -xVel;
            }
        } else if (x >= 650) {
            if (y >= p2.y && y <= p2.y + 80) {
                xVel = -xVel;
            }
        }
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
