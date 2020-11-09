package com.inkostilation.pong.commands;

public class ServerMessageCommand extends AbstractServerCommand {

    private String text;

    public ServerMessageCommand(String text) {
        this.text = text;
    }

    @Override
    public void execute() {
        System.out.println(text);
    }
}
