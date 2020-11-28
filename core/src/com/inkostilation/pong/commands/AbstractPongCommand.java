package com.inkostilation.pong.commands;

import com.inkostilation.pong.engine.IEngine;
import com.inkostilation.pong.engine.IPongEngine;

public abstract class AbstractPongCommand<M> extends AbstractServerCommand<IPongEngine<M>, M> {

}
