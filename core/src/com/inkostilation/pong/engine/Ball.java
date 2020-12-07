package com.inkostilation.pong.engine;

import com.inkostilation.pong.engine.geometry.Circle;
import com.inkostilation.pong.engine.geometry.Rectangle;

import java.util.Random;

import static java.lang.Math.PI;

public class Ball extends Circle {

    private static final float MAXBOUNCEANGLE = (float) (5*PI/12);

    private float velocity = 0.2f;
    private float xVel, yVel, defaultX, defaultY;


    public enum Direction {
        LEFT(-1), RIGHT(1);

        public final int value;

        Direction(int value) {
            this.value = value;
        }
    }

    public Ball(float x, float y) {
        super(x,y, 10);
        defaultX = x;
        defaultY = y;
        this.xVel = velocity * getRandomDirection();
        this.yVel = velocity * getRandomDirection();
    }

    public void resetPosition() {
        setX(defaultX);
        setY(defaultY);
    }

    public void resetVelocity(Direction direction) {
        yVel = 0;
        xVel = velocity * direction.value;
    }

    private int getRandomDirection() {
        if (new Random().nextBoolean())
            return 1;
        else
            return -1;
    }

    public void applyCollision(Rectangle rectangle) {
        float relativeIntersectY = rectangle.getY() + rectangle.getHeight() / 2 - getY();
        float normalizedRelativeIntersectionY = 2 * relativeIntersectY / rectangle.getHeight();
        float bounceAngle = normalizedRelativeIntersectionY * MAXBOUNCEANGLE;

        int newDir = xVel > 0? -1 : 1;

        xVel = (float) (newDir * velocity * Math.cos(bounceAngle));
        yVel = (float) (-velocity * Math.sin(bounceAngle));
    }

    public void move() {
        setX(getX() + xVel);
        setY(getY() + yVel);
    }

    public void constrain()
    {
        yVel = -yVel;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }
}
