package com.inkostilation.pong.commands;

import com.inkostilation.pong.notifications.INotifier;

public class ClientMessageCommand extends AbstractClientCommand {

    private String text;

    public ClientMessageCommand(String text) {
        this.text = text;
    }

    @Override
    public void execute() {
        System.out.println(text);
    }
}
