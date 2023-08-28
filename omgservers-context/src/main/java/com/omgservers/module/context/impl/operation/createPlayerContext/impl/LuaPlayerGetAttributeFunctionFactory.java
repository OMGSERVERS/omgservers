package com.omgservers.module.context.impl.operation.createPlayerContext.impl;

import com.omgservers.module.user.UserModule;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaPlayerGetAttributeFunctionFactory {

    final UserModule userModule;

    public LuaPlayerGetAttributeFunction build(Long userId, Long playerId) {
        final var function = new LuaPlayerGetAttributeFunction(userModule, userId, playerId);
        return function;
    }
}
