package com.omgservers.worker.module.handler.lua.luaCommand.impl;

import com.omgservers.worker.module.handler.lua.luaCommand.LuaCommand;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaValue;

@Getter
@ToString
public class HandleMessageLuaCommand extends LuaCommand {

    final Long userId;
    final Long clientId;

    public HandleMessageLuaCommand(final Long userId,
                                   final Long clientId,
                                   final LuaValue message) {
        super("handle_message", false);
        this.userId = userId;
        this.clientId = clientId;

        set("user_id", userId.toString());
        set("client_id", clientId.toString());
        set("message", message);
    }
}