package com.omgservers.module.script.impl.luaRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SignedInLuaRequest extends LuaRequest {

    final Long userId;
    final Long playerId;
    final Long clientId;

    public SignedInLuaRequest(Long userId, Long playerId, Long clientId) {
        super("signed_in", true);
        this.userId = userId;
        this.playerId = playerId;
        this.clientId = clientId;
        set("user_id", userId.toString());
        set("player_id", playerId.toString());
        set("client_id", clientId.toString());
    }
}
