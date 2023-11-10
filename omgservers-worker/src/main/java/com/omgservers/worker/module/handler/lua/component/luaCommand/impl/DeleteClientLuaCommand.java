package com.omgservers.worker.module.handler.lua.component.luaCommand.impl;

import com.omgservers.worker.module.handler.lua.component.luaCommand.LuaCommand;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeleteClientLuaCommand extends LuaCommand {

    final Long userId;
    final Long clientId;

    public DeleteClientLuaCommand(final Long userId,
                                  final Long clientId) {
        super("delete_client", true);
        this.userId = userId;
        this.clientId = clientId;

        set("user_id", userId.toString());
        set("client_id", clientId.toString());
    }
}
