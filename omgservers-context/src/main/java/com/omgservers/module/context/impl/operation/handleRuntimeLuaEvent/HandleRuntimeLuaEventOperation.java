package com.omgservers.module.context.impl.operation.handleRuntimeLuaEvent;

import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import io.smallrye.mutiny.Uni;

public interface HandleRuntimeLuaEventOperation {
    Uni<Boolean> handleRuntimeLuaEvent(Long runtimeId, LuaEvent luaEvent);
}
