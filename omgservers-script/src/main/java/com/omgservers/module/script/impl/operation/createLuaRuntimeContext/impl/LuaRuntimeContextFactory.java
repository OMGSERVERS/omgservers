package com.omgservers.module.script.impl.operation.createLuaRuntimeContext.impl;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaRuntimeContextFactory {

    final LuaRuntimeGetStateFunctionFactory getStateFunctionFactory;

    public LuaRuntimeContext build(final Long matchmakerId,
                                   final Long matchId,
                                   final Long runtimeId) {
        final var context = LuaRuntimeContext.builder()
                .matchmakerId(matchmakerId)
                .matchId(matchId)
                .runtimeId(runtimeId)
                .build();

        return context;
    }
}
