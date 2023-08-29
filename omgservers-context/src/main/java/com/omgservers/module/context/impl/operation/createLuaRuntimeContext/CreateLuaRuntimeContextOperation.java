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
                                                      Long runtimeI) {
        return createLuaRuntimeContext(matchmakerId, matchId, runtimeI)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
