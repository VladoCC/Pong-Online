package com.inkostilation.pong.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.inkostilation.pong.desktop.network.Network;
import com.inkostilation.pong.desktop.network.ServerEngine;
import com.inkostilation.pong.engine.IEngine;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new PongMain(), config);
	}
}
