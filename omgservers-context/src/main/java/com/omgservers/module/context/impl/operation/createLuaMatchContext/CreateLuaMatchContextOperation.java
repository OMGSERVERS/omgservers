package com.omgservers.module.context.impl.operation.createLuaMatchContext;

import com.omgservers.module.context.impl.operation.createLuaMatchContext.impl.LuaMatchContext;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface CreateLuaMatchContextOperation {
    Uni<LuaMatchContext> createLuaMatchContext(Long matchmakerId, Long matchId);

    default LuaMatchContext createLuaMatchContext(long timeout, Long matchmakerId, Long matchId) {
        return createLuaMatchContext(matchmakerId, matchId)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
