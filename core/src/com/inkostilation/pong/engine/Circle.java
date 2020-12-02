package com.inkostilation.pong.engine;

public class Circle implements IGeometricShape {

    private float x, y, radius;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
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


}
