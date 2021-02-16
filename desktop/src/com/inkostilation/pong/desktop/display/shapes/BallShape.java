package com.inkostilation.pong.desktop.display.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inkostilation.pong.desktop.display.Colors;
import com.inkostilation.pong.desktop.notification.ClientNotifier;
import com.inkostilation.pong.engine.Ball;
import com.inkostilation.pong.engine.GameState;
import com.inkostilation.pong.notifications.IObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;

public class BallShape extends AbstractShape implements IObserver<Ball> {

    private static final float KEEP_TIME = 0.5f;
    private static final float SPAWN_TIME = 0.05f;

    private static final int PART_COUNT = 100;

    private Ball observable;
    private boolean visible = true;

    private float moveTime = 0;

    private float lastX = 0;
    private float lastY = 0;
    private float lastXVel = 0;
    private float lastYVel = 0;

    private FieldShape parent;

    private List<Particle> particles = new ArrayList<>();

    public BallShape(FieldShape parent) {
        this.parent = parent;
        ClientNotifier.getInstance().subscribe(this, Ball.class);
    }

    @Override
    public DrawRect draw(DrawRect rect, ShapeRenderer renderer) {
        if (visible) {
            if (lastXVel * observable.getXVel() < 0) {
                float start = (float) (observable.getXVel() > 0? -PI / 2 : PI / 2);
                addGhost(start);
            }

            if (lastYVel * observable.getYVel() < 0) {
                float start = (float) (observable.getYVel() > 0? 0 : PI);
                addGhost(start);
            }

            float radius = observable.getRadius();

            /*for (Pair ghost : ghosts) {
                Color color = Color.GRAY;
                renderer.setColor(color);
                renderer.set(ShapeRenderer.ShapeType.Filled);
                renderer.circle(ghost.getBall().getX() + rect.getBottomLeft().getX(),
                        ghost.getBall().getY() + rect.getBottomLeft().getY(), radius);
                ghost.addTime(Gdx.graphics.getDeltaTime());
            }*/

            float x = observable.getX() + rect.getBottomLeft().getX();
            float y = observable.getY() + rect.getBottomLeft().getY();
            renderer.set(ShapeRenderer.ShapeType.Filled);

            renderer.setColor(Colors.getOutlineColor());
            renderer.circle(x, y, radius + 1);

            renderer.setColor(Colors.getLightColor());
            renderer.circle(x, y, radius);
        }

        if ((lastX != observable.getX() || lastY != observable.getY()) && visible) {
            moveTime += Gdx.graphics.getDeltaTime();
            if (moveTime > SPAWN_TIME) {
                moveTime -= SPAWN_TIME;

                if (particles.size() < PART_COUNT) {
                    Vector2 pos = new Vector2(observable.getX(), observable.getY());
                    float angle = new Random().nextFloat() * (float) PI / 3 - (float) PI / 6;
                    float cosa = lastX - observable.getX();
                    float sina = lastY - observable.getY();
                    Vector2 dir = new Vector2((float) (cosa * cos(angle) - sina * sin(angle)),
                            (float) (sina * cos(angle) + cosa * sin(angle)));
                    float rot = new Random().nextFloat() * (float) PI / 2;
                    float dist = new Random().nextFloat() * 20 + 10;
                    particles.add(new Particle(pos, dir, rot, dist));
                }
            }
        }

        for (Particle particle : particles) {
            Vector2 newPos = particle.getPos().getNewPosition(0, 0);
            newPos = newPos.getNewPosition(rect.getBottomLeft().getX(),
                    rect.getBottomLeft().getY());
            renderer.rect(newPos.getX(), newPos.getY(),
                    newPos.getX() + 1f, newPos.getY() + 1f,
                    2f, 2f, 1f, 1f, particle.getAngle());
            particle.addTime(Gdx.graphics.getDeltaTime());
        }
        particles.removeIf(p -> p.time >= KEEP_TIME);

        lastX = observable.getX();
        lastY = observable.getY();

        lastXVel = observable.getXVel();
        lastYVel = observable.getYVel();

        return rect;
    }

    private void addGhost(float start) {
        parent.addGhost(new Ghost(new Vector2(observable.getX(), observable.getY()), start));
    }

    @Override
    public void observe(Ball... observable) {
        this.observable = observable[0];
        setReady(true);
    }

    public Vector2 getPosition() {
        return new Vector2(observable.getX(), observable.getY());
    }

    public Vector2 getVelocity() {
        return new Vector2(observable.getXVel(), observable.getYVel());
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public class Ghost {
        private float time = 0;
        private Vector2 pos;
        private float start;

        public Ghost(Vector2 pos, float start) {
            this.pos = pos;
            this.start = start;
        }

        public void addTime(float add) {
            time += add;
        }

        public float getTime() {
            return time;
        }

        public Vector2 getPos() {
            return pos;
        }

        public float getStart() {
            return start;
        }
    }

    private class Particle{
        private float time = 0;
        private Vector2 pos;
        private Vector2 dir;
        private float rotation;
        private float angle;
        private float dist;

        public Particle(Vector2 pos, Vector2 dir, float rotation, float dist) {
            this.pos = pos;
            this.dir = dir;
            this.rotation = rotation;
            this.dist = dist;
        }

        public void addTime(float add) {
            time += add;
            angle += rotation * add;
        }

        public Vector2 getPos(){
            return pos.getNewPosition(dist * dir.getX() * time / KEEP_TIME, dist * dir.getY() * time / KEEP_TIME);
        }

        public float getAngle() {
            return angle;
        }
    }
}
