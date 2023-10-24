package com.omgservers.module.script.impl.luaRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class DeleteClientLuaRequest extends LuaRequest {

    final Long userId;
    final Long clientId;

    public DeleteClientLuaRequest(final Long userId,
                                  final Long clientId) {
        super("delete_client", true);
        this.userId = userId;
        this.clientId = clientId;

        set("user_id", userId.toString());
        set("client_id", clientId.toString());
    }
}
