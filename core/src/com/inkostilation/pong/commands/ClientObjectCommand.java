package com.inkostilation.pong.commands;

import com.inkostilation.pong.notifications.INotifier;

public class ClientObjectCommand extends AbstractClientCommand {

    private Object object;

    public ClientObjectCommand(Object object) {
        this.object = object;
    }

    @Override
    public void execute() {
        System.out.println(object.toString());
    }
}