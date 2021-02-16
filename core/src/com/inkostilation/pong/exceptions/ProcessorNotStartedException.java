package com.inkostilation.pong.exceptions;

public class ProcessorNotStartedException extends Exception {

    public ProcessorNotStartedException() {
        super("Can't process connections while processor isn't started");
    }
}
