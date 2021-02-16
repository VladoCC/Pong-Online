package com.inkostilation.pong.exceptions;

public class ProcessorStartedException extends Exception {

    public ProcessorStartedException() {
        super("Can't start processor, which is already started");
    }
}
