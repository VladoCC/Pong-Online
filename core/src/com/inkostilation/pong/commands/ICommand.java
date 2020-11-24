package com.inkostilation.pong.commands;

import java.io.IOException;

public interface ICommand {

    void execute() throws IOException;
}
