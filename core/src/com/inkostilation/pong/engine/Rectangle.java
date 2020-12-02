package com.inkostilation.pong.engine;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Rectangle implements IGeometricShape{

    private float x, y, width, height;

    public Rectangle (float x, float y, float width, float height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void constrain(int fieldHeight)
    {
        if (y < 0) {
            y = 0;
        } else if (y > fieldHeight - height) {
            y = fieldHeight - height;
        }
    }

    @Override
    public boolean isInBounds(int fieldHeight) {
        if ((y < 0) || (y > fieldHeight - height))
            return false;
        return true;
    }

    public void setY(float y) {
        this.y = y;
    }
}
