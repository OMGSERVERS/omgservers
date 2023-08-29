package com.omgservers.module.context.impl.operation.handleLuaEvent;

import com.omgservers.module.context.impl.operation.createLuaGlobals.CreateLuaGlobalsOperation;
import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleLuaEventOperationImpl implements HandleLuaEventOperation {

    final CreateLuaGlobalsOperation createLuaGlobalsOperation;

    @Override
    public Uni<Void> handleLuaEvent(final Long tenantId,
                                    final Long stageId,
                                    final LuaEvent luaEvent,
                                    final LuaTable context) {
        // TODO: cache/reuse by some cache key
        return createLuaGlobalsOperation.createLuaGlobals(tenantId, stageId)
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(luaRuntime -> luaRuntime.handleEvent(luaEvent, context))
                .replaceWithVoid();
    }
}
