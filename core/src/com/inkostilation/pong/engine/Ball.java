package com.inkostilation.pong.engine;

import java.util.Random;

import static java.lang.Math.PI;

public class Ball extends Circle {

    private static final float MAXBOUNCEANGLE = (float) (5*PI/12);

    private float xVel, yVel;

    public Ball(float x, float y) {
        super(x,y, 10);
        this.xVel = getRandomSpeed() * getRandomDirection();
        this.yVel = getRandomSpeed() * getRandomDirection();
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

    public void applyCollision(Rectangle rectangle) {
        float relativeIntersectY = (rectangle.getY()+(rectangle.getHeight()/2)) - getY();
        float normalizedRelativeIntersectionY = (relativeIntersectY/(rectangle.getHeight()/2));
        float bounceAngle = normalizedRelativeIntersectionY * MAXBOUNCEANGLE;
        xVel = (float) (xVel*Math.cos(bounceAngle));
        yVel = (float) (yVel*-Math.sin(bounceAngle));
    }

    public void move() {
        setX(getX()+xVel);
        setY(getY()+yVel);
    }

    public void constrain()
    {
        yVel = -yVel;
    }
}
