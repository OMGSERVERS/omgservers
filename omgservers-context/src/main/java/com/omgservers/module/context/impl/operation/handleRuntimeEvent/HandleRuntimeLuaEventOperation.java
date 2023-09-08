package com.omgservers.module.context.impl.operation.handleRuntimeEvent;

import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import io.smallrye.mutiny.Uni;

public interface HandleRuntimeLuaEventOperation {
    Uni<Boolean> handleRuntimeLuaEvent(Long tenantId,
                                       Long versionId,
                                       Long matchmakerId,
                                       Long matchId,
                                       Long runtimeId,
                                       LuaEvent luaEvent);
}
