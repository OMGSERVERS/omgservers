package com.omgservers.module.context.impl.luaEvent.runtime;

import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class LuaHandleEventRuntimeCommandReceivedEvent extends LuaEvent {

    final Long userId;
    final Long playerId;
    final Long clientId;
    final String data;

    public LuaHandleEventRuntimeCommandReceivedEvent(final Long userId,
                                                     final Long playerId,
                                                     final Long clientId,
                                                     final String data) {
        super("runtime_handle_event");
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
