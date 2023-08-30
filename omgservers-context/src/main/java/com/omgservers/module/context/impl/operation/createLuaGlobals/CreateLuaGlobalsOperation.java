package com.omgservers.module.context.impl.operation.createLuaGlobals;

import com.omgservers.module.context.impl.operation.createLuaGlobals.impl.LuaGlobals;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface CreateLuaGlobalsOperation {
    Uni<LuaGlobals> createLuaGlobals(Long versionId);

    default LuaGlobals createLuaGlobals(long timeout, Long versionId) {
        return createLuaGlobals(versionId)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
