package com.omgservers.module.script.impl.luaRequest;

import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

@Getter
@ToString
public class AddClientLuaRequest extends LuaRequest {

    final Long userId;
    final Long clientId;
    final LuaTable attributes;
    final LuaValue object;

    public AddClientLuaRequest(final Long userId,
                               final Long clientId,
                               final LuaTable attributes,
                               final LuaValue object) {
        super("add_client", true);
        this.userId = userId;
        this.clientId = clientId;
        this.attributes = attributes;
        this.object = object;

        set("user_id", userId.toString());
        set("client_id", clientId.toString());
        set("attributes", attributes);
        set("object", object);
    }
}
