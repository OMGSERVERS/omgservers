package com.omgservers.module.handler.impl.operation.createLuaMatchContext.impl;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaMatchContextFactory {

    public LuaMatchContext build(Long matchmakerId, Long matchId) {
        final var context = LuaMatchContext.builder()
                .build();

        return context;
    }
}
