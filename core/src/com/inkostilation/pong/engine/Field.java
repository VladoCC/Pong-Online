package com.inkostilation.pong.engine;

import com.inkostilation.pong.engine.geometry.Rectangle;

public class Field extends Rectangle {

    private Paddle paddle1, paddle2;
    private Ball ball;

    public Field()
    {
        super(0,0,700,500);
        this.paddle1 = new Paddle(20, 210);
        this.paddle2 = new Paddle(660, 210);
        this.ball = new Ball(350, 250);
    }

    public Paddle getPaddle1() {
        return paddle1;
    }

    public Paddle getPaddle2() {
        return paddle2;
    }

    public Ball getBall() {
        return ball;
    }

    public boolean isBallInBounds() {
        return ball.isInBounds(this);
    }

    public void run() {
        paddle1.move();
        paddle2.move();
        ball.move();

        if (!paddle1.isInBounds(this)) {
            paddle1.constrain(this);
        }

        if (!paddle2.isInBounds(this)) {
            paddle2.constrain(this);
        }

        if (!ball.isInBounds(this)) {
            onBallOutOfBounds();
        }

        if (ball.isColliding(paddle1)) {
            ball.applyCollision(paddle1);
        }

        if (ball.isColliding(paddle2)) {
            ball.applyCollision(paddle2);
        }
    }

    private void onBallOutOfBounds() {
        if (ball.getY() - ball.getRadius() < getY()
                || ball.getY() + ball.getRadius() > getY() + getHeight()) {
            ball.constrain();
        }
    }

    public void reset() {
        paddle1.reset();
        paddle2.reset();

        ball.resetPosition();
        ball.resetVelocity(ball.getX() - ball.getRadius() < getX()?
                Ball.Direction.LEFT : Ball.Direction.RIGHT);

    }
}
