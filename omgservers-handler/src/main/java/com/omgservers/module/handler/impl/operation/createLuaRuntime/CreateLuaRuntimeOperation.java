package com.omgservers.module.handler.impl.operation.createLuaRuntime;

import com.omgservers.module.handler.impl.operation.createLuaRuntime.impl.LuaRuntime;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface CreateLuaRuntimeOperation {
    Uni<LuaRuntime> createLuaRuntime(Long tenantId, Long stageId);

    default LuaRuntime createLuaRuntime(long timeout, Long tenantId, Long stageId) {
        return createLuaRuntime(tenantId, stageId)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
