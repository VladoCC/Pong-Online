package com.inkostilation.pong.commands;


import com.inkostilation.pong.engine.PlayerRole;

public class ResponsePlayerRoleCommand extends AbstractResponseCommand{

    private PlayerRole playerRole;

    public ResponsePlayerRoleCommand(int player) {
        this.playerRole.setNumber(player);
    }

    @Override
    public void execute() {
        //something
    }
}
