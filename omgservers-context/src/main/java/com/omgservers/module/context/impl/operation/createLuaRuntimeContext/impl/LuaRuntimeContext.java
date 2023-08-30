package com.omgservers.module.context.impl.operation.createLuaRuntimeContext.impl;

import lombok.Builder;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

@Slf4j
@Builder
@ToString
public class LuaRuntimeContext extends LuaTable {

    final Long matchmakerId;
    final Long matchId;
    final Long runtimeId;

    public LuaRuntimeContext(final Long matchmakerId,
                             final Long matchId,
                             final Long runtimeId) {
        this.matchmakerId = matchmakerId;
        this.matchId = matchId;
        this.runtimeId = runtimeId;
        set("matchmaker_id", matchmakerId.toString());
        set("match_id", matchId.toString());
        set("runtime_id", runtimeId.toString());
    }
}
