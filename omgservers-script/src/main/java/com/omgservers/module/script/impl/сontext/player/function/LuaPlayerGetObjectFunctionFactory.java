package com.omgservers.module.script.impl.сontext.player.function;

import com.omgservers.module.script.impl.operation.coerceJavaObject.CoerceJavaObjectOperation;
import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import com.omgservers.module.user.UserModule;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaPlayerGetObjectFunctionFactory {

    final UserModule userModule;

    final CoerceJavaObjectOperation coerceJavaObjectOperation;
    final HandleLuaCallOperation handleLuaCallOperation;

    public LuaPlayerGetObjectFunction build(Long userId, Long playerId) {
        final var function = new LuaPlayerGetObjectFunction(
                userModule,
                coerceJavaObjectOperation,
                handleLuaCallOperation,
                userId,
                playerId);
        return function;
    }
}
