package com.omgservers.module.handler.impl.operation.createLuaRuntime.impl.event;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class LuaPlayerSignedUpEvent extends LuaEvent {

    final Long userId;
    final Long playerId;
    final Long clientId;

    public LuaPlayerSignedUpEvent(Long userId, Long playerId, Long clientId) {
        super("player_signed_up");
        this.userId = userId;
        this.playerId = playerId;
        this.clientId = clientId;
        set("user_id", userId.toString());
        set("player_id", playerId.toString());
        set("client_id", clientId.toString());
    }
}
