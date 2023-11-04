package com.omgservers.worker.module.handler.lua.luaRequest.impl;

import com.omgservers.worker.module.handler.lua.luaRequest.LuaRequest;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaValue;

@Getter
@ToString
public class HandleMessageLuaRequest extends LuaRequest {

    final Long userId;
    final Long clientId;

    public HandleMessageLuaRequest(final Long userId,
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
