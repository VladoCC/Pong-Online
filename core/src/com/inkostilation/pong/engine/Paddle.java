package com.inkostilation.pong.engine;

public class Paddle extends Rectangle {

    private static final float FRICTION = 0.94f;

    private float yVel;

    private PlayerRole playerRole;

    private boolean controlled;

    private Direction accelerationDirection;

    public Paddle(float x, float y) {
        super(x, y, 20, 80);
        this.yVel = 0;
        accelerationDirection = Direction.IDLE;
        this.controlled = false;
    }

    public void setAccelerationDirection(Direction accelerationDirection) {
        this.accelerationDirection = accelerationDirection;
    }

    public PlayerRole getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(PlayerRole playerRole) { this.playerRole = playerRole; }

    public void setControlled(boolean controlled) {
        this.controlled = controlled;
    }

    public boolean isControlled() {
        return controlled;
    }

    public void move() {
        int dir = accelerationDirection.value;
        if (dir != 0)
            yVel += 2 * dir;
        else
            yVel *= FRICTION;

        if (yVel >= 5)
            yVel = 5;
        else if (yVel <= -5)
            yVel = -5;

        setY(getY()+yVel);
    }

    public void constrain(Rectangle rectangle)
    {
        if (getY() < 0) {
            setY(0);
        }
        else if (getY() > rectangle.getHeight() - getHeight()) {
            setY(rectangle.getHeight() - getHeight());
        }
    }
}
