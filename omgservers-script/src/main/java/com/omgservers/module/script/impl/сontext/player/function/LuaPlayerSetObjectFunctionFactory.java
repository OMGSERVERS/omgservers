package com.omgservers.module.script.impl.сontext.player.function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import com.omgservers.module.user.UserModule;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaPlayerSetObjectFunctionFactory {

    final UserModule userModule;

    final HandleLuaCallOperation handleLuaCallOperation;
    final ObjectMapper objectMapper;

    public LuaPlayerSetObjectFunction build(Long userId, Long playerId) {
        final var function = new LuaPlayerSetObjectFunction(
                userModule,
                handleLuaCallOperation,
                objectMapper,
                userId,
                playerId);
        return function;
    }
}
