package com.omgservers.module.script.impl.luaEvent.runtime;

import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddPlayerLuaEvent extends LuaEvent {

    final Long userId;
    final Long playerId;
    final Long clientId;

    public AddPlayerLuaEvent(final Long userId,
                             final Long playerId,
                             final Long clientId) {
        super("add_player");
        this.userId = userId;
        this.playerId = playerId;
        this.clientId = clientId;

        set("user_id", userId.toString());
        set("player_id", playerId.toString());
        set("client_id", clientId.toString());
    }
}
