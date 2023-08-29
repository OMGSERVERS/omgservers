package com.omgservers.module.context.impl.operation.createLuaRuntimeContext.impl;

import com.omgservers.module.context.impl.operation.createLuaRuntimeContext.CreateLuaRuntimeContextOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLuaRuntimeContextOperationImpl implements CreateLuaRuntimeContextOperation {

    final LuaRuntimeContextFactory luaRuntimeContextFactory;

    @Override
    public Uni<LuaRuntimeContext> createLuaRuntimeContext(final Long matchmakerId,
                                                          final Long matchId,
                                                          final Long runtimeId) {
        if (matchmakerId == null) {
            throw new IllegalArgumentException("matchmakerId is null");
        }
        if (matchId == null) {
            throw new IllegalArgumentException("matchId is null");
        }
        if (runtimeId == null) {
            throw new IllegalArgumentException("runtimeId is null");
        }

        return Uni.createFrom().voidItem()
                .map(voidItem -> luaRuntimeContextFactory.build(matchmakerId, matchId, runtimeId))
                .invoke(luaRuntimeContext -> log.info("Lua runtime context was created, {}", luaRuntimeContext));
    }
}
