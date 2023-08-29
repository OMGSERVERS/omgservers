package com.omgservers.module.context.impl.operation.createLuaRuntimeContext.impl;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaRuntimeContextFactory {

    public LuaRuntimeContext build(Long matchmakerId, Long matchId, Long runtimeId) {
        final var context = LuaRuntimeContext.builder()
                .matchmakerId(matchmakerId)
                .matchId(matchId)
                .runtimeId(runtimeId)
                .build();

        return context;
    }
}
