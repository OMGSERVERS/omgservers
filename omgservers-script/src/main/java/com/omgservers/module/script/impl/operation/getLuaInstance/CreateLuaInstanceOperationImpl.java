package com.omgservers.module.script.impl.operation.getLuaInstance;

import com.omgservers.model.script.ScriptModel;
import com.omgservers.module.script.impl.operation.createLuaGlobals.CreateLuaGlobalsOperation;
import com.omgservers.module.script.impl.operation.createLuaPlayerContext.CreateLuaPlayerContextOperation;
import com.omgservers.module.script.impl.operation.createLuaRuntimeContext.CreateLuaRuntimeContextOperation;
import com.omgservers.module.script.impl.operation.selectScript.SelectScriptOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaTable;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLuaInstanceOperationImpl implements CreateLuaInstanceOperation {

    final CreateLuaRuntimeContextOperation createLuaRuntimeContextOperation;
    final CreateLuaPlayerContextOperation createLuaPlayerContextOperation;
    final CreateLuaGlobalsOperation createLuaGlobalsOperation;
    final SelectScriptOperation selectScriptOperation;

    @Override
    public Uni<LuaInstance> createLuaInstance(final ScriptModel script) {
        final var tenantId = script.getTenantId();
        final var versionId = script.getVersionId();
        return createLuaGlobalsOperation.createLuaGlobals(tenantId, versionId)
                .flatMap(luaGlobals -> createLuaContext(script)
                        .map(luaContext -> new LuaInstance(luaGlobals, luaContext)));
    }

    Uni<LuaTable> createLuaContext(final ScriptModel script) {
        final var config = script.getConfig();
        return switch (script.getType()) {
            case CLIENT -> createLuaPlayerContextOperation
                    .createLuaPlayerContext(config.getUserId(), config.getPlayerId(), config.getClientId())
                    .map(luaContext -> (LuaTable) luaContext);
            case RUNTIME -> createLuaRuntimeContextOperation
                    .createLuaRuntimeContext(config.getMatchmakerId(), config.getMatchId(), config.getRuntimeId())
                    .map(luaContext -> (LuaTable) luaContext);
        };
    }
}
