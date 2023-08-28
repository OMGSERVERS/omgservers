package com.omgservers.module.context.impl.operation.createPlayerContext.impl;

import com.omgservers.module.user.UserModule;
import com.omgservers.module.user.factory.AttributeModelFactory;
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
