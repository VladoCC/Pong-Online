package com.inkostilation.pong.commands;

import com.inkostilation.pong.engine.IEngine;

public class ServerMessageCommand extends AbstractServerCommand {

    private String text;

    public ServerMessageCommand(String text) {
        this.text = text;
    }

    @Override
    public void execute(IEngine engine) {
        System.out.println(text);
    }
}
