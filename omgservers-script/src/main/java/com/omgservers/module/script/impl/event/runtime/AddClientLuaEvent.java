package com.omgservers.module.script.impl.event.runtime;

import com.omgservers.module.script.impl.event.LuaEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class AddClientLuaEvent extends LuaEvent {

    final Long userId;
    final Long playerId;
    final Long clientId;

    public AddClientLuaEvent(final Long userId,
                             final Long playerId,
                             final Long clientId) {
        super("add_client");
        this.userId = userId;
        this.playerId = playerId;
        this.clientId = clientId;

        set("user_id", userId.toString());
        set("player_id", playerId.toString());
        set("client_id", clientId.toString());
    }
}
