package com.omgservers.worker.module.handler.lua.luaCommand.impl;

import com.omgservers.worker.module.handler.lua.luaCommand.LuaCommand;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpLuaCommand extends LuaCommand {

    final Long userId;
    final Long clientId;

    public SignUpLuaCommand(Long userId, Long clientId) {
        super("sign_up", true);
        this.userId = userId;
        this.clientId = clientId;

        set("user_id", userId.toString());
        set("client_id", clientId.toString());
    }
}
