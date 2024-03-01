package com.omgservers.worker.module.handler.lua.component.luaCommand.impl;

import com.omgservers.worker.module.handler.lua.component.luaCommand.LuaCommand;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DeleteClientLuaCommand extends LuaCommand {

    final Long clientId;

    public DeleteClientLuaCommand(final Long clientId) {
        super("delete_client", true);
        this.clientId = clientId;

        set("client_id", clientId.toString());
    }
}
