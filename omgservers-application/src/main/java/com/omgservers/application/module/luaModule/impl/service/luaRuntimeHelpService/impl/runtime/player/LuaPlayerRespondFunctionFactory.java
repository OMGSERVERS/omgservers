package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.player;

import com.omgservers.application.module.userModule.UserModule;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class LuaPlayerRespondFunctionFactory {

    final UserModule userModule;

    public LuaPlayerRespondFunction build(UUID user, UUID client) {
        final var function = new LuaPlayerRespondFunction(userModule, user, client);
        return function;
    }
}
