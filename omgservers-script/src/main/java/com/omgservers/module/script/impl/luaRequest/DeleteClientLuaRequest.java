package com.omgservers.module.script.impl.luaRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class DeleteClientLuaRequest extends LuaRequest {

    final Long clientId;

    public DeleteClientLuaRequest(final Long clientId) {
        super("delete_client", true);
        this.clientId = clientId;

        set("client_id", clientId.toString());
    }
}
