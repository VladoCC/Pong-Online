package com.inkostilation.pong.server;

import com.inkostilation.pong.server.engine.PongEngine;
import com.inkostilation.pong.server.network.NetworkProcessor;

import java.io.IOException;

public class ServerLauncher {
	public static void main (String[] arg) throws IOException {
		//LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//new LwjglApplication(new PongMain(), config);
		NetworkProcessor processor = NetworkProcessor.getInstance();
		processor.start();

		while (true) {
			if (processor.isStarted()) {
				processor.processConnections();
				PongEngine.getInstance().act();
			}
		}
	}
}