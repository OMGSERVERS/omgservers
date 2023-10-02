package com.omgservers.module.script.impl.event.player;

import com.omgservers.module.script.impl.event.LuaEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.luaj.vm2.LuaValue;

@Getter
@Builder
@ToString
public class ChangePlayerLuaEvent extends LuaEvent {

    final Long userId;
    final Long playerId;
    final Long clientId;
    final LuaValue data;

    public ChangePlayerLuaEvent(final Long userId,
                                final Long playerId,
                                final Long clientId,
                                final LuaValue data) {
        super("change_player");
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
