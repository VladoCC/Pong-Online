package com.inkostilation.pong.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;

import static java.lang.Math.PI;

public class Circle implements IGeometricShape {

    private static final float MAXBOUNCEANGLE = (float) (5*PI/12);

    private float x, y, xVel, yVel, radius;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Circle(float x, float y, float radius)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
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

    public boolean isColliding(Rectangle rectangle, int fieldWidth) {
        if ((rectangle.getX() < fieldWidth / 2)) {
            if (x <= rectangle.getX() + rectangle.getWidth() + radius)
                return y >= rectangle.getY() && y <= rectangle.getY() + rectangle.getHeight();
            return false;
        } else if (x >= rectangle.getX() - radius)
            return y >= rectangle.getY() && y <= rectangle.getY() + rectangle.getHeight();
        return false;
    }

    @Override
    public boolean isInBounds(int fieldWidth) {
        if ((y < radius) || (y > fieldWidth - radius))
            return false;
        return true;
    }

    public void applyCollision(Rectangle rectangle) {
        float relativeIntersectY = (rectangle.getY()+(rectangle.getHeight()/2)) - y;
        float normalizedRelativeIntersectionY = (relativeIntersectY/(rectangle.getHeight()/2));
        float bounceAngle = normalizedRelativeIntersectionY * MAXBOUNCEANGLE;
        xVel = (float) (xVel*Math.cos(bounceAngle));
        yVel = (float) (yVel*-Math.sin(bounceAngle));
    }

    @Override
    public void move() {
        x += xVel;
        y += yVel;
    }

    public void constrain()
    {
        yVel = -yVel;
    }

}
