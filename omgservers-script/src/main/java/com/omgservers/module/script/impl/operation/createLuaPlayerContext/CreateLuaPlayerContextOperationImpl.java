package com.omgservers.module.script.impl.operation.createLuaPlayerContext;

import com.omgservers.module.script.impl.сontext.player.LuaPlayerContext;
import com.omgservers.module.script.impl.сontext.player.LuaPlayerContextFactory;
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
                .map(voidItem -> luaPlayerContextFactory.build(userId, playerId, clientId));
    }
}
