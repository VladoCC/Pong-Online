package com.inkostilation.pong.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

import static java.lang.Math.PI;

public class Ball {
    private static final float MAXBOUNCEANGLE = (float) (5*PI/12);
    private float xVel, yVel, x, y, radius = 10;

    public Ball(float x, float y) {
        this.x = x;
        this.y = y;
        xVel = getRandomSpeed() * getRandomDirection();
        yVel = getRandomSpeed() * getRandomDirection();
    }

    private float getRandomSpeed()
    {
        return new Random().nextFloat() * 3 + 2;
    }

    private int getRandomDirection() {
        if (new Random().nextBoolean())
            return 1;
        else
            return -1;
    }

    public boolean isColliding(Paddle paddle, int width) {
        if (paddle.getX() < width / 2 ) {
            if (x <= paddle.getX() + paddle.getWidth() + radius)
                return y >= paddle.getY() && y <= paddle.getY() + paddle.getHeight();
            return false;
        }
        else
            if (x >= paddle.getX() - radius)
                return y >= paddle.getY() && y <= paddle.getY() + paddle.getHeight();
            return false;
    }

    public void applyCollision(Paddle paddle) {
        float relativeIntersectY = (paddle.getY()+(paddle.getHeight()/2)) - y;
        float normalizedRelativeIntersectionY = (relativeIntersectY/(paddle.getHeight()/2));
        float bounceAngle = normalizedRelativeIntersectionY * MAXBOUNCEANGLE;
        xVel = (float) (xVel*Math.cos(bounceAngle));
        yVel = (float) (yVel*-Math.sin(bounceAngle));
    }

    public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(x - radius, y - radius, radius);
    }

    public void move() {
        x += xVel;
        y += yVel;
    }

    public boolean isInBounds(int width) {
        if ((y < radius) || (y > width - radius))
            return false;
        return true;
    }

    public void constrain()
    {
        yVel = -yVel;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }
}
