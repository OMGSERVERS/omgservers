package com.omgservers.module.script.impl.luaEvent.runtime;

import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import lombok.Builder;
import lombok.Getter;
import org.luaj.vm2.LuaValue;

@Getter
@Builder
public class HandleMessageLuaEvent extends LuaEvent {

    final Long userId;
    final Long playerId;
    final Long clientId;
    final LuaValue data;

    public HandleMessageLuaEvent(final Long userId,
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
