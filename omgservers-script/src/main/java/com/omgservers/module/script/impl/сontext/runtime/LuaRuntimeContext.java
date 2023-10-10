package com.omgservers.module.script.impl.сontext.runtime;

import com.omgservers.module.script.impl.сontext.runtime.function.LuaRuntimeBroadcastMessageFunction;
import com.omgservers.module.script.impl.сontext.runtime.function.LuaRuntimeKickClientFunction;
import com.omgservers.module.script.impl.сontext.runtime.function.LuaRuntimeMulticastMessageFunction;
import com.omgservers.module.script.impl.сontext.runtime.function.LuaRuntimeStopRuntimeFunction;
import com.omgservers.module.script.impl.сontext.runtime.function.LuaRuntimeUnicastMessageFunction;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

@Slf4j
@Getter
@Builder
@ToString
public class LuaRuntimeContext extends LuaTable {

    final Long matchmakerId;
    final Long matchId;
    final Long runtimeId;

    @ToString.Exclude
    final LuaRuntimeUnicastMessageFunction unicastMessageFunction;
    @ToString.Exclude
    final LuaRuntimeMulticastMessageFunction multicastMessageFunction;
    @ToString.Exclude
    final LuaRuntimeBroadcastMessageFunction broadcastMessageFunction;
    @ToString.Exclude
    final LuaRuntimeKickClientFunction kickClientFunction;
    @ToString.Exclude
    final LuaRuntimeStopRuntimeFunction stopRuntimeFunction;

    public LuaRuntimeContext(final Long matchmakerId,
                             final Long matchId,
                             final Long runtimeId,
                             final LuaRuntimeUnicastMessageFunction unicastMessageFunction,
                             final LuaRuntimeMulticastMessageFunction multicastMessageFunction,
                             final LuaRuntimeBroadcastMessageFunction broadcastMessageFunction,
                             final LuaRuntimeKickClientFunction kickClientFunction,
                             final LuaRuntimeStopRuntimeFunction stopRuntimeFunction) {
        this.matchmakerId = matchmakerId;
        this.matchId = matchId;
        this.runtimeId = runtimeId;
        set("matchmaker_id", matchmakerId.toString());
        set("match_id", matchId.toString());
        set("runtime_id", runtimeId.toString());

        this.unicastMessageFunction = unicastMessageFunction;
        this.multicastMessageFunction = multicastMessageFunction;
        this.broadcastMessageFunction = broadcastMessageFunction;
        this.kickClientFunction = kickClientFunction;
        this.stopRuntimeFunction = stopRuntimeFunction;

        set("unicast_message", unicastMessageFunction);
        set("multicast_message", multicastMessageFunction);
        set("broadcast_message", broadcastMessageFunction);
        set("kick_client", kickClientFunction);
        set("stop_runtime", stopRuntimeFunction);
    }
}
