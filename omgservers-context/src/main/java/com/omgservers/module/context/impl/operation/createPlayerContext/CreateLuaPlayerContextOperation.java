package com.omgservers.module.context.impl.operation.createPlayerContext;

import com.omgservers.module.context.impl.operation.createPlayerContext.impl.LuaPlayerContext;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface CreateLuaPlayerContextOperation {
    Uni<LuaPlayerContext> createLuaPlayerContext(Long userId, Long playerId, Long clientId);

    default LuaPlayerContext createLuaPlayerContext(long timeout,
                                                    Long userId,
                                                    Long playerId,
                                                    Long clientId) {
        return createLuaPlayerContext(userId, playerId, clientId)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
