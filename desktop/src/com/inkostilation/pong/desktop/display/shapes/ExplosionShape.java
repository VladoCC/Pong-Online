package com.inkostilation.pong.desktop.display.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inkostilation.pong.desktop.display.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;

public class ExplosionShape extends AbstractShape {

    private static final int PARTS = 800;
    private static final float DURATION = 1.0f;
    private static final float DISTANCE = 2;

    private float time = 0;

    private Vector2 start;
    private List<Particle> particles = new ArrayList<Particle>();

    public ExplosionShape() {
        setReady(true);
    }

    public void start(Vector2 start, Vector2 dir, float distance) {
        time = 0;
        this.start = start;

        for (int i = 0; i < PARTS; i++) {
            double angle = new Random().nextFloat() * PI - PI / 2;
            Vector2 particleDir = new Vector2((float) (dir.getX() * Math.cos(angle) - dir.getY() * Math.sin(angle)),
                    (float) (dir.getY() * cos(angle) + dir.getX() * sin(angle)));
            float dist = distance / 2 + (new Random().nextFloat() * distance);
            particleDir.mult(dist);

            float rot = (float) (new Random().nextFloat() * 360);
            float duration = new Random().nextFloat() * DURATION;
            particles.add(new Particle(particleDir, rot, duration));
        }
    }

    @Override
    public DrawRect draw(DrawRect rect, ShapeRenderer renderer) {
        if (time <= DURATION) {
            renderer.setColor(Colors.getLightColor());
            time += Gdx.graphics.getDeltaTime();
            for (Particle particle : particles) {
                if (time <= particle.getDuration()) {
                    Vector2 move = particle.getMove();
                    Vector2 newPos = start.getNewPosition(rect.getBottomLeft().getX() + move.getX() * time,
                            rect.getBottomLeft().getY() + move.getY() * time);

                    renderer.rect(newPos.getX(), newPos.getY(),
                            1f, 1f,
                            2f, 2f, 1f, 1f, particle.getRotation() * time);

                }
            }
        }

        return rect;
    }

    private class Particle {
        private Vector2 move;
        private float rotation;
        private float duration;

        public Particle(Vector2 move, float rotation, float duration) {
            this.move = move;
            this.rotation = rotation;
            this.duration = duration;
        }

        public Vector2 getMove() {
            return move;
        }

        public float getRotation() {
            return rotation;
        }

        public float getDuration() {
            return duration;
        }
    }
}
