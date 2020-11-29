package com.inkostilation.pong.engine;

public class Field {
    private int width = 700, height = 500;
    private Paddle paddle1, paddle2;
    private Ball ball;

    public Field()
    {
        this.paddle1 = new Paddle(20, 210);
        this.paddle2 = new Paddle(660, 210);
        this.ball = new Ball(350, 250);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
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

    public void run() {
        paddle1.move();
        paddle2.move();
        ball.move();
        if (!paddle1.isInBounds(height))
            paddle1.constrain(height);
        if (!paddle2.isInBounds(height))
            paddle2.constrain(height);
        if (!ball.isInBounds(width))
            ball.constrain();
        if (ball.isColliding(paddle1, width))
            ball.applyCollision(paddle1);
        if (ball.isColliding(paddle2, width))
            ball.applyCollision(paddle2);
    }

}
