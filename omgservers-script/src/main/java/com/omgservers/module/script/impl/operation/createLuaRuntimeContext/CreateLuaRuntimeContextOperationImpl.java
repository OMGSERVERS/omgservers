package com.omgservers.module.script.impl.operation.createLuaRuntimeContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.module.script.impl.operation.createLuaRuntimeContext.CreateLuaRuntimeContextOperation;
import com.omgservers.module.script.impl.luaContext.runtime.LuaRuntimeContext;
import com.omgservers.module.script.impl.luaContext.runtime.LuaRuntimeContextFactory;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLuaRuntimeContextOperationImpl implements CreateLuaRuntimeContextOperation {

    final LuaRuntimeContextFactory luaRuntimeContextFactory;

    final ObjectMapper objectMapper;

    @Override
    public Uni<LuaRuntimeContext> createLuaRuntimeContext(final Long matchmakerId,
                                                          final Long matchId,
                                                          final Long runtimeId) {
        return Uni.createFrom().voidItem()
                .map(voidItem -> luaRuntimeContextFactory.build(matchmakerId, matchId, runtimeId))
                .invoke(luaRuntimeContext -> log.info("Lua runtime context was created, {}", luaRuntimeContext));
    }
}
