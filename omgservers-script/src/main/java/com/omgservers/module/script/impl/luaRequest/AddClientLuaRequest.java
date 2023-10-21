package com.omgservers.module.script.impl.luaRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AddClientLuaRequest extends LuaRequest {

    final Long userId;
    final Long clientId;

    public AddClientLuaRequest(final Long userId,
                               final Long clientId) {
        super("add_client", true);
        this.userId = userId;
        this.clientId = clientId;

        set("user_id", userId.toString());
        set("client_id", clientId.toString());
    }
}
