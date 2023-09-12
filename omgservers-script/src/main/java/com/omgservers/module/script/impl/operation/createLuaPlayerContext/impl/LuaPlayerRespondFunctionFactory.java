package com.omgservers.module.script.impl.operation.createLuaPlayerContext.impl;

import com.omgservers.module.user.UserModule;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class LuaPlayerRespondFunctionFactory {

    final UserModule userModule;

    public LuaPlayerRespondFunction build(Long userId, Long clientId) {
        final var function = new LuaPlayerRespondFunction(userModule, userId, clientId);
        return function;
    }
}
