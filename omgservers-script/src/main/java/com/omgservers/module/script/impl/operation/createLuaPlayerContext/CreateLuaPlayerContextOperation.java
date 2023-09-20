package com.omgservers.module.script.impl.operation.createLuaPlayerContext;

import com.omgservers.module.script.impl.сontext.player.LuaPlayerContext;
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
