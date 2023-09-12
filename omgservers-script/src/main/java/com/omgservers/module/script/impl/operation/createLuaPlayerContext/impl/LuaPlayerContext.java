package com.omgservers.module.script.impl.operation.createLuaPlayerContext.impl;

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
    final LuaPlayerSetAttributeFunction setAttributeFunction;
    @ToString.Exclude
    final LuaPlayerGetAttributeFunction getAttributeFunction;

    public LuaPlayerContext(final Long userId,
                            final Long playerId,
                            final Long clientId,
                            final LuaPlayerRespondFunction respondFunction,
                            final LuaPlayerSetAttributeFunction setAttributeFunction,
                            final LuaPlayerGetAttributeFunction getAttributeFunction) {
        this.userId = userId;
        this.playerId = playerId;
        this.clientId = clientId;

        set("user_id", userId);
        set("player_id", playerId);
        set("client_id", clientId);

        this.respondFunction = respondFunction;
        this.setAttributeFunction = setAttributeFunction;
        this.getAttributeFunction = getAttributeFunction;

        set("respond", respondFunction);
        set("set_attribute", setAttributeFunction);
        set("get_attribute", getAttributeFunction);
    }
}
