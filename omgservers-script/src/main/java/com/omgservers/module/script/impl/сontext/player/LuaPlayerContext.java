package com.omgservers.module.script.impl.сontext.player;

import com.omgservers.module.script.impl.сontext.player.function.LuaPlayerGetAttributesFunction;
import com.omgservers.module.script.impl.сontext.player.function.LuaPlayerRespondFunction;
import com.omgservers.module.script.impl.сontext.player.function.LuaPlayerSetAttributesFunction;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

@Slf4j
@Getter
@Builder
@ToString
public class LuaPlayerContext extends LuaTable {

    final Long userId;
    final Long playerId;
    final Long clientId;

    @ToString.Exclude
    final LuaPlayerRespondFunction respondFunction;
    @ToString.Exclude
    final LuaPlayerSetAttributesFunction setAttributesFunction;
    @ToString.Exclude
    final LuaPlayerGetAttributesFunction getAttributesFunction;

    public LuaPlayerContext(final Long userId,
                            final Long playerId,
                            final Long clientId,
                            final LuaPlayerRespondFunction respondFunction,
                            final LuaPlayerSetAttributesFunction setAttributesFunction,
                            final LuaPlayerGetAttributesFunction getAttributesFunction) {
        this.userId = userId;
        this.playerId = playerId;
        this.clientId = clientId;

        set("user_id", userId);
        set("player_id", playerId);
        set("client_id", clientId);

        this.respondFunction = respondFunction;
        this.setAttributesFunction = setAttributesFunction;
        this.getAttributesFunction = getAttributesFunction;

        set("respond", respondFunction);
        set("set_attributes", setAttributesFunction);
        set("get_attributes", getAttributesFunction);
    }
}
