package com.omgservers.worker.module.handler.lua.component.luaCommand.impl;

import com.omgservers.worker.module.handler.lua.component.luaCommand.LuaCommand;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaValue;

@Getter
@ToString
public class AddClientLuaCommand extends LuaCommand {

    final Long clientId;
    final String groupName;

    public AddClientLuaCommand(final Long clientId,
                               final LuaValue attributes,
                               final LuaValue profile) {
        this(clientId, attributes, profile, null);
    }

    public AddClientLuaCommand(final Long clientId,
                               final LuaValue attributes,
                               final LuaValue profile,
                               final String groupName) {
        super("add_client", true);
        this.clientId = clientId;
        this.groupName = groupName;

        set("client_id", clientId.toString());
        set("attributes", attributes);
        set("profile", profile);

        set("group_name", groupName);
    }
}
