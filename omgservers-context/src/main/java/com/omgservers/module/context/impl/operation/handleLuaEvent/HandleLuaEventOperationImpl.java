package com.omgservers.module.context.impl.operation.handleLuaEvent;

import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import com.omgservers.module.context.impl.operation.createLuaGlobals.CreateLuaGlobalsOperation;
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
    public Uni<Void> handleLuaEvent(final Long versionId,
                                    final LuaEvent luaEvent,
                                    final LuaTable context) {
        // TODO: cache/reuse by some cache key
        return createLuaGlobalsOperation.createLuaGlobals(versionId)
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(luaGlobals -> luaGlobals.handleEvent(luaEvent, context))
                .replaceWithVoid();
    }
}
