package com.inkostilation.pong.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle {

        final float GRAVITY = 0.94f;

        float x, y, yVel;
        boolean upAccel, downAccel;
        int player;

        public Paddle(int player) {
            upAccel = false;
            downAccel = false;
            y = 210;
            yVel = 0;

            if (player == 1)
                x = 20;
            else
                x = 660;
        }

        public void draw(ShapeRenderer shapeRenderer) {
            shapeRenderer.setColor(Color.WHITE);
            shapeRenderer.rect(x, y, 20, 80);
        }

        public void move() {
            if (upAccel) {
                yVel -= 2;
            } else if (downAccel) {
                yVel += 2;
            } else if (!upAccel && !downAccel) {
                yVel *= GRAVITY;
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

        public void setUpAccel(boolean input) {
            upAccel = input;
        }

        public void setDownAccel(boolean input) {
            downAccel = input;
        }

}
