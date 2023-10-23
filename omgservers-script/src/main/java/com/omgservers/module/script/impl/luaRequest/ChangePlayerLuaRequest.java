package com.omgservers.module.script.impl.luaRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

@Getter
@Builder
@ToString
public class ChangePlayerLuaRequest extends LuaRequest {

    final Long userId;
    final Long clientId;
    final LuaTable attributes;
    final LuaValue object;
    final LuaValue message;

    public ChangePlayerLuaRequest(final Long userId,
                                  final Long clientId,
                                  final LuaTable attributes,
                                  final LuaValue object,
                                  final LuaValue message) {
        super("change_player", false);
        this.userId = userId;
        this.clientId = clientId;
        this.attributes = attributes;
        this.object = object;
        this.message = message;

        set("user_id", userId.toString());
        set("client_id", clientId.toString());
        set("attributes", attributes);
        set("object", object);
        set("message", message);
    }
}
