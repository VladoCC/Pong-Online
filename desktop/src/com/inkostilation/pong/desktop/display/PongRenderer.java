package com.inkostilation.pong.desktop.display;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.inkostilation.pong.desktop.notification.ClientNotifier;
import com.inkostilation.pong.engine.PlayerRole;
import com.inkostilation.pong.notifications.IObserver;

public class PongRenderer extends ShapeRenderer implements IObserver<PlayerRole> {

    public PongRenderer() {
        ClientNotifier.getInstance().subscribe(this, PlayerRole.class);
    }

    @Override
    public void observe(PlayerRole... observable) {
        PlayerRole role = observable[0];
        System.out.println(role.toString());
        if (role == PlayerRole.SECOND) {
            System.out.println("Flipped");
            scale(-1, 1,1);
            translate(-1280, 0, 0);
        }
    }
}
