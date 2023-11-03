package com.omgservers.worker.module.handler.lua.luaRequest.impl;

import com.omgservers.worker.module.handler.lua.luaRequest.LuaRequest;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SignUpLuaRequest extends LuaRequest {

    final Long userId;
    final Long clientId;

    public SignUpLuaRequest(Long userId, Long clientId) {
        super("sign_up", true);
        this.userId = userId;
        this.clientId = clientId;

        set("user_id", userId.toString());
        set("client_id", clientId.toString());
    }
}
