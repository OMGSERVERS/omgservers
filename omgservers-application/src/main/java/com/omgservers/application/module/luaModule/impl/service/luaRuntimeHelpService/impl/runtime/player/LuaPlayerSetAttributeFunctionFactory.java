package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.player;

import com.omgservers.application.factory.AttributeModelFactory;
import com.omgservers.application.module.userModule.UserModule;
import com.omgservers.operation.generateId.GenerateIdOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class LuaPlayerSetAttributeFunctionFactory {

    final UserModule userModule;
    final AttributeModelFactory attributeModelFactory;
    final GenerateIdOperation generateIdOperation;

    public LuaPlayerSetAttributeFunction build(Long userId, Long playerId) {
        final var function = new LuaPlayerSetAttributeFunction(userModule, attributeModelFactory, generateIdOperation, userId, playerId);
        return function;
    }
}
