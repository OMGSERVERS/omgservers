package com.omgservers.module.script.impl.operation.getLuaInstance;

import com.omgservers.model.script.ScriptModel;
import com.omgservers.module.script.impl.operation.createLuaGlobals.CreateLuaGlobalsOperation;
import com.omgservers.module.script.impl.operation.selectScript.SelectScriptOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLuaInstanceOperationImpl implements CreateLuaInstanceOperation {

    final CreateLuaGlobalsOperation createLuaGlobalsOperation;
    final SelectScriptOperation selectScriptOperation;

    @Override
    public Uni<LuaInstance> createLuaInstance(final ScriptModel script) {
        final var tenantId = script.getTenantId();
        final var versionId = script.getVersionId();
        return createLuaGlobalsOperation.createLuaGlobals(tenantId, versionId)
                .map(LuaInstance::new);
    }
}
