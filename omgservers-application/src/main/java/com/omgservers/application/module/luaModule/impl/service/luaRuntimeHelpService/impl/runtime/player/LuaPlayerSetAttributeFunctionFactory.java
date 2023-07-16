package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.player;

import com.omgservers.application.module.userModule.UserModule;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaPlayerSetAttributeFunctionFactory {

    final UserModule userModule;

    public LuaPlayerSetAttributeFunction build(UUID user, UUID player) {
        final var function = new LuaPlayerSetAttributeFunction(userModule, user, player);
        return function;
    }
}
