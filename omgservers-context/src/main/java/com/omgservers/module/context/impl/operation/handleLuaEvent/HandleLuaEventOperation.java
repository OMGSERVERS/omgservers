package com.omgservers.module.context.impl.operation.handleLuaEvent;

import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import io.smallrye.mutiny.Uni;
import org.luaj.vm2.LuaTable;

public interface HandleLuaEventOperation {
    Uni<Void> handleLuaEvent(Long tenantId, Long stageId, LuaEvent luaEvent, LuaTable context);
}
