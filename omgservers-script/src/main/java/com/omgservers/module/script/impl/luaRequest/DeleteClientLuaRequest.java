package com.omgservers.module.script.impl.luaRequest;

import com.omgservers.module.script.impl.luaRequest.LuaRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class DeleteClientLuaRequest extends LuaRequest {

    final Long clientId;

    public DeleteClientLuaRequest(final Long clientId) {
        super("delete_client");
        this.clientId = clientId;

        set("client_id", clientId.toString());
    }
}
