package com.omgservers.module.context.impl.operation.handlePlayerLuaEvent;

import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import io.smallrye.mutiny.Uni;

public interface HandlePlayerLuaEventOperation {
    Uni<Boolean> handlePlayerLuaEvent(Long tenantId,
                                      Long versionId,
                                      Long userId,
                                      Long playerId,
                                      Long clientId,
                                      LuaEvent luaEvent);
}
