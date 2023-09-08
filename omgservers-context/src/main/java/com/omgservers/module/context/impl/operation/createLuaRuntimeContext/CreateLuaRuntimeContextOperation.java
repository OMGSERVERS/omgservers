package com.omgservers.module.context.impl.operation.createLuaRuntimeContext;

import com.omgservers.module.context.impl.operation.createLuaRuntimeContext.impl.LuaRuntimeContext;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface CreateLuaRuntimeContextOperation {
    Uni<LuaRuntimeContext> createLuaRuntimeContext(Long matchmakerId,
                                                   Long matchId,
                                                   Long runtimeId);

    default LuaRuntimeContext createLuaRuntimeContext(long timeout,
                                                      Long matchmakerId,
                                                      Long matchId,
                                                      Long runtimeId) {
        return createLuaRuntimeContext(matchmakerId, matchId, runtimeId)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
