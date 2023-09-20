package com.omgservers.module.script.impl.сontext.player.function;

import com.omgservers.module.gateway.factory.MessageModelFactory;
import com.omgservers.module.script.impl.operation.handleLuaCallOperation.HandleLuaCallOperation;
import com.omgservers.module.user.UserModule;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaPlayerRespondFunctionFactory {

    final UserModule userModule;

    final HandleLuaCallOperation handleLuaCallOperation;

    final MessageModelFactory messageModelFactory;

    public LuaPlayerRespondFunction build(Long userId, Long clientId) {
        final var function = LuaPlayerRespondFunction.builder()
                .userModule(userModule)
                .handleLuaCallOperation(handleLuaCallOperation)
                .messageModelFactory(messageModelFactory)
                .userId(userId)
                .clientId(clientId)
                .build();

        return function;
    }
}
