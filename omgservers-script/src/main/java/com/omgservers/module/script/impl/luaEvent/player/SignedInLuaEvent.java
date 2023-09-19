package com.omgservers.module.script.impl.luaEvent.player;

import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SignedInLuaEvent extends LuaEvent {

    final Long userId;
    final Long playerId;
    final Long clientId;

    public SignedInLuaEvent(Long userId, Long playerId, Long clientId) {
        super("signed_in");
        this.userId = userId;
        this.playerId = playerId;
        this.clientId = clientId;
        set("user_id", userId.toString());
        set("player_id", playerId.toString());
        set("client_id", clientId.toString());
    }
}
