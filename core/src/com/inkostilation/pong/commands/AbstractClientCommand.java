package com.inkostilation.pong.commands;

import com.inkostilation.pong.notifications.INotifier;

public abstract class AbstractClientCommand implements ICommand {

    private INotifier notifier;

    public AbstractClientCommand() {
    }

    protected INotifier getNotifier() {
        return notifier;
    }

    public void setNotifier(INotifier notifier) {
        this.notifier = notifier;
    }
}
