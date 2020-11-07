package com.inkostilation.pong.server;

import com.inkostilation.pong.server.network.NetworkProcessor;

public class ServerLauncher {
	public static void main (String[] arg) {
		//LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//new LwjglApplication(new PongMain(), config);
		NetworkProcessor processor = new NetworkProcessor();
		processor.start();

		while (true) {
			if (processor.isStarted()) {
				processor.processConnectons();
			}
		}
	}
}
