package com.omgservers.module.script.impl.сontext.player.function;

import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import com.omgservers.module.user.UserModule;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaPlayerSetAttributesFunctionFactory {

    final UserModule userModule;

    final HandleLuaCallOperation handleLuaCallOperation;

    public LuaPlayerSetAttributesFunction build(Long userId, Long playerId) {
        final var function = new LuaPlayerSetAttributesFunction(
                userModule,
                handleLuaCallOperation,
                userId,
                playerId);
        return function;
    }
}
