package com.omgservers.module.context.impl.luaEvent.runtime;

import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class LuaDeletePlayerRuntimeCommandReceivedEvent extends LuaEvent {

    final Long userId;
    final Long playerId;
    final Long clientId;

    public LuaDeletePlayerRuntimeCommandReceivedEvent(final Long userId,
                                                      final Long playerId,
                                                      final Long clientId) {
        super("runtime_delete_player");
        this.userId = userId;
        this.playerId = playerId;
        this.clientId = clientId;
        set("user_id", userId.toString());
        set("player_id", playerId.toString());
        set("client_id", clientId.toString());
    }
}
