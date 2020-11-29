package com.inkostilation.pong.commands;

public class ResponseObjectCommand extends AbstractResponseCommand {

    private Object object;

    public ResponseObjectCommand(Object object) {
        this.object = object;
    }

    @Override
    public void execute() {
        System.out.println(object.toString());
    }
}