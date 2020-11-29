package com.inkostilation.pong.commands;

import com.inkostilation.pong.engine.IEngine;

public class RequestMessageCommand extends AbstractRequestCommand {

    private String text;

    public RequestMessageCommand(String text) {
        this.text = text;
    }

    @Override
    public void execute(IEngine engine) {
        System.out.println(text);
    }
}
