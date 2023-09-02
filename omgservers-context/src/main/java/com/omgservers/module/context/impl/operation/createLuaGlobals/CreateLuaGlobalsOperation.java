package com.omgservers.module.context.impl.operation.createLuaGlobals;

import com.omgservers.module.context.impl.operation.createLuaGlobals.impl.LuaGlobals;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface CreateLuaGlobalsOperation {
    Uni<LuaGlobals> createLuaGlobals(Long tenantId, Long versionId);

    default LuaGlobals createLuaGlobals(long timeout, Long tenantId, Long versionId) {
        return createLuaGlobals(tenantId, versionId)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
