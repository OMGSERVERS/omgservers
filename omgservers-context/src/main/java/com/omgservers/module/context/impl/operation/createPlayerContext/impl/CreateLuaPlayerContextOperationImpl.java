package com.omgservers.module.context.impl.operation.createPlayerContext.impl;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.module.context.impl.operation.createPlayerContext.CreateLuaPlayerContextOperation;
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
        if (userId == null) {
            throw new ServerSideBadRequestException("userId is null");
        }
        if (playerId == null) {
            throw new ServerSideBadRequestException("playerId is null");
        }
        if (clientId == null) {
            throw new ServerSideBadRequestException("clientId is null");
        }

        return Uni.createFrom().voidItem()
                .map(voidItem -> luaPlayerContextFactory.build(userId, playerId, clientId))
                .invoke(luaPlayerContext -> log.info("Lua player context was created, {}", luaPlayerContext));
    }
}
