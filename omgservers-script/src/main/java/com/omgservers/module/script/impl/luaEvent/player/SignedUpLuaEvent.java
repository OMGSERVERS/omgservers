package com.omgservers.module.script.impl.luaEvent.player;

import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
public class SignedUpLuaEvent extends LuaEvent {

    final Long userId;
    final Long playerId;
    final Long clientId;

    public SignedUpLuaEvent(Long userId, Long playerId, Long clientId) {
        super("signed_up");
        this.userId = userId;
        this.playerId = playerId;
        this.clientId = clientId;

        set("user_id", userId.toString());
        set("player_id", playerId.toString());
        set("client_id", clientId.toString());
    }
}
