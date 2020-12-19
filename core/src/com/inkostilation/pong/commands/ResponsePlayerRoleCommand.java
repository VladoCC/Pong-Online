package com.inkostilation.pong.commands;

import com.inkostilation.pong.engine.PlayerRole;

public class ResponsePlayerRoleCommand extends AbstractResponseCommand{

    private PlayerRole playerRole;

    public ResponsePlayerRoleCommand(PlayerRole player) {
        this.playerRole = player;
    }

    @Override
    public void execute() { System.out.println(playerRole.toString());}
}
