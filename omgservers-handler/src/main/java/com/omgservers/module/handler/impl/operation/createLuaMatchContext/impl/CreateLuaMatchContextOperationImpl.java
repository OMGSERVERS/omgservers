package com.omgservers.module.handler.impl.operation.createLuaMatchContext.impl;

import com.omgservers.module.handler.impl.operation.createLuaMatchContext.CreateLuaMatchContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLuaMatchContextOperationImpl implements CreateLuaMatchContextOperation {

    final LuaMatchContextFactory luaMatchContextFactory;

    @Override
    public Uni<LuaMatchContext> createLuaMatchContext(final Long matchmakerId, final Long matchId) {
        if (matchmakerId == null) {
            throw new IllegalArgumentException("matchmakerId is null");
        }
        if (matchId == null) {
            throw new IllegalArgumentException("matchId is null");
        }

        return Uni.createFrom().voidItem()
                .map(voidItem -> luaMatchContextFactory.build(matchmakerId, matchId))
                .invoke(luaMatchContext -> log.info("Lua match context was created, {}", luaMatchContext));
    }
}
