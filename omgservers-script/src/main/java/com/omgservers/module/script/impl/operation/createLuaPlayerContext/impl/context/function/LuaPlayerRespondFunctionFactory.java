package com.omgservers.module.script.impl.operation.createLuaPlayerContext.impl.context.function;

import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.user.UserModule;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaPlayerRespondFunctionFactory {

    final UserModule userModule;

    final MessageModelFactory messageModelFactory;

    public LuaPlayerRespondFunction build(Long userId, Long clientId) {
        final var function = new LuaPlayerRespondFunction(userModule, messageModelFactory, userId, clientId);
        return function;
    }
}
