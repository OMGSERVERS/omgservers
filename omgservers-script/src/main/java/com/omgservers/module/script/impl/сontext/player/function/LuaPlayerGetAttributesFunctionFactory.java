package com.omgservers.module.script.impl.сontext.player.function;

import com.omgservers.module.user.UserModule;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaPlayerGetAttributesFunctionFactory {

    final UserModule userModule;

    public LuaPlayerGetAttributesFunction build(Long userId, Long playerId) {
        final var function = new LuaPlayerGetAttributesFunction(userModule, userId, playerId);
        return function;
    }
}
