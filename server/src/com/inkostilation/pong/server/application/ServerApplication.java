package com.inkostilation.pong.server.application;

import com.inkostilation.pong.engine.IEngine;
import com.inkostilation.pong.exceptions.ProcessorNotStartedException;
import com.inkostilation.pong.exceptions.ProcessorStartedException;
import com.inkostilation.pong.server.engine.PongEngine;
import com.inkostilation.pong.server.network.NetworkProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerApplication {

    private List<NetworkProcessor> processors = new ArrayList<>();
    private Map<Class<IEngine>, IEngine> engines = new HashMap<>();

    private boolean started = false;

    public void start() {
        started = true;
        loop();
    }

    public void addServerProcessor(String host, int port) {
        try {
            NetworkProcessor processor = new NetworkProcessor();
            processor.start(host, port);
            processors.add(processor);
        } catch (ProcessorStartedException e) {
            e.printStackTrace();
        }
    }

    public void addEngine(IEngine engine) {
        Class<IEngine> engineClass = (Class<IEngine>) engine.getClass();
        if (engines.containsKey(engineClass)) {
            engines.put(engineClass, engine);
        }
    }

    private void loop() {
        while (started) {
            processors.forEach((processor) -> {
                if (processor.isStarted()) {
                    try {
                        processor.processConnections();
                    } catch (ProcessorNotStartedException e) {
                        e.printStackTrace();
                    }
                }
            });
            engines.values().forEach(e -> e.act(Time.getDeltaTime()));
            Time.updateTime();
        }
    }

    public void stop() {
        processors.forEach(NetworkProcessor::stop);

        started = false;
    }
}
