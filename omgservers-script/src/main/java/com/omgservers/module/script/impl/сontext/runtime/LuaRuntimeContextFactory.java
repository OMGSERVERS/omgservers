package com.omgservers.module.script.impl.сontext.runtime;

import com.omgservers.module.script.impl.сontext.runtime.function.LuaRuntimeBroadcastMessageFunctionFactory;
import com.omgservers.module.script.impl.сontext.runtime.function.LuaRuntimeKickClientFunctionFactory;
import com.omgservers.module.script.impl.сontext.runtime.function.LuaRuntimeMulticastMessageFunctionFactory;
import com.omgservers.module.script.impl.сontext.runtime.function.LuaRuntimeUnicastMessageFunctionFactory;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaRuntimeContextFactory {

    final LuaRuntimeUnicastMessageFunctionFactory luaRuntimeUnicastMessageFunctionFactory;
    final LuaRuntimeMulticastMessageFunctionFactory luaRuntimeMulticastMessageFunctionFactory;
    final LuaRuntimeBroadcastMessageFunctionFactory luaRuntimeBroadcastMessageFunctionFactory;
    final LuaRuntimeKickClientFunctionFactory luaRuntimeKickClientFunctionFactory;

    public LuaRuntimeContext build(final Long matchmakerId,
                                   final Long matchId,
                                   final Long runtimeId) {
        final var context = LuaRuntimeContext.builder()
                .matchmakerId(matchmakerId)
                .matchId(matchId)
                .runtimeId(runtimeId)
                .unicastMessageFunction(luaRuntimeUnicastMessageFunctionFactory.build(runtimeId))
                .multicastMessageFunction(luaRuntimeMulticastMessageFunctionFactory.build(runtimeId))
                .broadcastMessageFunction(luaRuntimeBroadcastMessageFunctionFactory.build(runtimeId))
                .kickClientFunction(luaRuntimeKickClientFunctionFactory.build(runtimeId))
                .build();

        return context;
    }
}
