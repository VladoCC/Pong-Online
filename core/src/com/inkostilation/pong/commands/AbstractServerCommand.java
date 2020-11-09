package com.inkostilation.pong.commands;

import com.inkostilation.pong.engine.IEngine;

public abstract class AbstractServerCommand<M> implements ICommand {

    private IEngine<M> engine;
    private M marker;

    public void setEngine(IEngine<M> engine) {
        this.engine = engine;
    }

    public void setMarker(M marker) {
        this.marker = marker;
    }

    protected IEngine<M> getEngine() {
        return engine;
    }

    public M getMarker() {
        return marker;
    }
}
