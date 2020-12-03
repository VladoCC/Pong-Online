package com.inkostilation.pong.engine;

public class Circle extends AbstractGeometricShape {

    private float radius;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Circle(float x, float y, float radius)
    {
        super(x,y);
        this.radius = radius;
    }

    public boolean isColliding(Rectangle rectangle) {
        if (getY() >= rectangle.getY() && getY() <= rectangle.getY() + rectangle.getHeight() && (getX() <= rectangle.getX() + rectangle.getWidth() + radius || getX()>= rectangle.getX() - radius))
                return true;
        return false;
    }

    @Override
    public boolean isInBounds(Rectangle rectangle) {
        if ((getX() < radius) || (getX() > rectangle.getWidth() - radius))
            return false;
        return true;
    }


}
