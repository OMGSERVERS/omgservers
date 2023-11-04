package com.omgservers.worker.module.handler.lua.luaCommand.impl;

import com.omgservers.worker.module.handler.lua.luaCommand.LuaCommand;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

@Getter
@ToString
public class ChangePlayerLuaCommand extends LuaCommand {

    final Long userId;
    final Long clientId;

    public ChangePlayerLuaCommand(final Long userId,
                                  final Long clientId,
                                  final LuaTable attributes,
                                  final LuaValue object,
                                  final LuaValue message) {
        super("change_player", false);
        this.userId = userId;
        this.clientId = clientId;

        set("user_id", userId.toString());
        set("client_id", clientId.toString());
        set("attributes", attributes);
        set("object", object);
        set("message", message);
    }
}