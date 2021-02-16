package com.inkostilation.pong.desktop.display.shapes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.inkostilation.pong.desktop.display.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.PI;

public class ImplosionShape extends AbstractShape {

    private static final int PARTS = 800;
    private static final float IDLE = 1.5f;
    private static final float LIFE_TIME = 0.7f;

    private static final float DISTANCE = 100;

    private boolean working = false;
    private float idleClock = 0;

    private List<Particle> particles = new ArrayList<>();

    public ImplosionShape() {
        for (int i = 0; i < PARTS; i++) {
            float time = -new Random().nextFloat() * LIFE_TIME;
            float angle = new Random().nextFloat() * 2 * (float) PI;
            float rot = new Random().nextFloat() * (float) PI / 2;
            Vector2 pos = new Vector2((float) Math.cos(angle),(float) Math.sin(angle));
            Vector2 path = pos.getNewPosition(0,0);
            path.mult(-1);
            particles.add(new Particle(path, pos, time, rot));
        }

        setReady(true);
    }

    @Override
    public DrawRect draw(DrawRect rect, ShapeRenderer renderer) {

        if (working) {
            System.out.println("Clock: " + idleClock);

            if (idleClock >= IDLE) {
                particles.forEach(p -> p.setActive(true));
            } else {
                idleClock += Gdx.graphics.getDeltaTime();
            }
        }

        renderer.setColor(Colors.getLightColor());
        for (Particle particle : particles) {
            if (particle.isActive()) {
                if (particle.getTime() >= 0) {
                    Vector2 dims = new Vector2(rect.getTopRight().getX() - rect.getBottomLeft().getX(),
                            rect.getTopRight().getY() - rect.getBottomLeft().getY());
                    Vector2 newPos = particle.getPos().getNewPosition(0, 0);
                    newPos.mult(dims.getY() / 4);
                    newPos = newPos.getNewPosition(rect.getBottomLeft().getX() + dims.getX() / 2,
                            rect.getBottomLeft().getY() + dims.getY() / 2);
                    renderer.rect(newPos.getX(), newPos.getY(),
                            newPos.getX() + 1f, newPos.getY() + 1f,
                            2f, 2f, 1f, 1f, particle.getAngle());
                }
                particle.addTime(Gdx.graphics.getDeltaTime());
                if (particle.getTime() >= LIFE_TIME) {
                    particle.addTime(-LIFE_TIME);
                    if (!working) {
                        particle.setActive(false);
                    }
                }
            }
        }


        return rect;
    }

    public void setWorking(boolean working) {
        System.out.println("Working: "  + working);
        this.working = working;
        idleClock = 0;
    }

    private class Particle {
        private Vector2 path;
        private Vector2 start;
        private float time;
        private float defaultTime;
        private float rotation;
        private float angle;
        private boolean active = false;

        public Particle(Vector2 path, Vector2 start, float time, float rotation) {
            this.path = path;
            this.start = start;
            this.defaultTime = time;
            this.time = time;
            this.rotation = rotation;
        }

        public void addTime(float add) {
            time += add;
            angle += rotation * add;
        }

        public Vector2 getPos(){
            return start.getNewPosition(path.getX() * time / LIFE_TIME, path.getY() * time / LIFE_TIME);
        }

        public Vector2 getPath() {
            return path;
        }

        public Vector2 getStart() {
            return start;
        }

        public float getTime() {
            return time;
        }

        public float getRotation() {
            return rotation;
        }

        public float getAngle() {
            return angle;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
            if (!active) {
                time = defaultTime;
            }
        }
    }
}
