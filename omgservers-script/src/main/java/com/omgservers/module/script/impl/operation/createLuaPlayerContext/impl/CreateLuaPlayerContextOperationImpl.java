package com.omgservers.module.script.impl.operation.createLuaPlayerContext.impl;

import com.omgservers.module.script.impl.operation.createLuaPlayerContext.CreateLuaPlayerContextOperation;
import com.omgservers.module.script.impl.operation.createLuaPlayerContext.impl.context.LuaPlayerContext;
import com.omgservers.module.script.impl.operation.createLuaPlayerContext.impl.context.LuaPlayerContextFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLuaPlayerContextOperationImpl implements CreateLuaPlayerContextOperation {

    final LuaPlayerContextFactory luaPlayerContextFactory;

    @Override
    public Uni<LuaPlayerContext> createLuaPlayerContext(final Long userId,
                                                        final Long playerId,
                                                        final Long clientId) {
        return Uni.createFrom().voidItem()
                .map(voidItem -> luaPlayerContextFactory.build(userId, playerId, clientId))
                .invoke(luaPlayerContext -> log.info("Lua player context was created, {}", luaPlayerContext));
    }
}
