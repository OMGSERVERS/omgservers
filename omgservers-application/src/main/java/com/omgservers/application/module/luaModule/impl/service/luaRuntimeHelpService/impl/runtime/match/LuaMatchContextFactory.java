package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.match;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaMatchContextFactory {

    public LuaMatchContext build(UUID matchmaker, UUID match) {
        final var context = LuaMatchContext.builder()
                .build();

        return context;
    }
}
