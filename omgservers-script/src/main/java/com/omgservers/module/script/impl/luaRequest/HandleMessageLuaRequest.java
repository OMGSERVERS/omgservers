package com.omgservers.module.script.impl.luaRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaValue;

@Getter
@Builder
@ToString
public class HandleMessageLuaRequest extends LuaRequest {

    final Long userId;
    final Long playerId;
    final Long clientId;
    final LuaValue data;

    public HandleMessageLuaRequest(final Long userId,
                                   final Long playerId,
                                   final Long clientId,
                                   final LuaValue data) {
        super("handle_message");
        this.userId = userId;
        this.playerId = playerId;
        this.clientId = clientId;
        this.data = data;

        set("user_id", userId.toString());
        set("player_id", playerId.toString());
        set("client_id", clientId.toString());
        set("data", data);
    }
}
