package com.omgservers.worker.module.handler.lua.luaRequest.impl;

import com.omgservers.worker.module.handler.lua.luaRequest.LuaRequest;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

@Getter
@ToString
public class SignInLuaRequest extends LuaRequest {

    final Long userId;
    final Long clientId;

    public SignInLuaRequest(final Long userId,
                            final Long clientId,
                            final LuaTable attributes,
                            final LuaValue object) {
        super("sign_in", true);
        this.userId = userId;
        this.clientId = clientId;

        set("user_id", userId.toString());
        set("client_id", clientId.toString());
        set("attributes", attributes);
        set("object", object);
    }
}
