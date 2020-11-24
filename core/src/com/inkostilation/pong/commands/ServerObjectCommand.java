package com.inkostilation.pong.commands;

public class ServerObjectCommand extends AbstractServerCommand {

    private Object object;

    public ServerObjectCommand(String text) {
        this.object = object;
    }

    @Override
    public void execute() {
        System.out.println(object.toString());
    }
}
