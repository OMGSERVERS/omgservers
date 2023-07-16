package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.player;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaPlayerContextFactory {

    final LuaPlayerRespondFunctionFactory respondFunctionFactory;
    final LuaPlayerSetAttributeFunctionFactory setAttributeFunctionFactory;
    final LuaPlayerGetAttributeFunctionFactory getAttributeFunctionFactory;

    public LuaPlayerContext build(UUID user, UUID player, UUID client) {
        final var context = LuaPlayerContext.builder()
                .respondFunction(respondFunctionFactory.build(user, client))
                .setAttributeFunction(setAttributeFunctionFactory.build(user, player))
                .getAttributeFunction(getAttributeFunctionFactory.build(user, player))
                .build();

        return context;
    }
}
