package com.omgservers.module.context.impl.operation.createLuaPlayerContext.impl;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaPlayerContextFactory {

    final LuaPlayerRespondFunctionFactory respondFunctionFactory;
    final LuaPlayerSetAttributeFunctionFactory setAttributeFunctionFactory;
    final LuaPlayerGetAttributeFunctionFactory getAttributeFunctionFactory;

    public LuaPlayerContext build(Long userId, Long playerId, Long clientId) {
        final var context = LuaPlayerContext.builder()
                .userId(userId)
                .playerId(playerId)
                .clientId(clientId)
                .respondFunction(respondFunctionFactory.build(userId, clientId))
                .setAttributeFunction(setAttributeFunctionFactory.build(userId, playerId))
                .getAttributeFunction(getAttributeFunctionFactory.build(userId, playerId))
                .build();

        return context;
    }
}
