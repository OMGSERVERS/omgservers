package com.omgservers.module.context.impl.operation.createLuaGlobals.impl;

import com.omgservers.dto.tenant.GetVersionBytecodeRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeResponse;
import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.module.context.impl.operation.createLuaGlobals.CreateLuaGlobalsOperation;
import com.omgservers.module.context.impl.operation.decodeLuaBytecode.DecodeLuaBytecodeOperation;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.operation.createServerGlobals.CreateServerGlobalsOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaValue;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLuaGlobalsOperationImpl implements CreateLuaGlobalsOperation {

    final TenantModule tenantModule;

    final CreateServerGlobalsOperation createServerGlobalsOperation;
    final DecodeLuaBytecodeOperation decodeLuaBytecodeOperation;

    @Override
    public Uni<LuaGlobals> createLuaGlobals(final Long tenantId, final Long versionId) {
        // TODO: Use version from runtime
        return getVersionBytecode(tenantId, versionId)
                .map(versionBytecode -> {
                    final var base64Files = versionBytecode.getFiles();
                    log.info("Creating lua globals, countOfFiles={}", base64Files.size());
                    final var luaBytecode = decodeLuaBytecodeOperation.decodeLuaBytecode(base64Files);
                    // TODO: use user globals?
                    final var globals = createServerGlobalsOperation.createServerGlobals();
                    globals.finder = new LuaResourceFinder(luaBytecode);
                    final var luaGlobals = new LuaGlobals(tenantId, versionId, globals);
                    // TODO: use param for main file
                    luaGlobals.getGlobals().get("dofile").call(LuaValue.valueOf("main.lua"));
                    return luaGlobals;
                })
                .invoke(luaGlobals -> log.info("Lua globals was created, " +
                        "tenantId={}, versionId={}, {}", tenantId, versionId, luaGlobals));
    }

    Uni<VersionBytecodeModel> getVersionBytecode(final Long tenantId, final Long versionId) {
        final var request = new GetVersionBytecodeRequest(tenantId, versionId);
        return tenantModule.getVersionService().getVersionBytecode(request)
                .map(GetVersionBytecodeResponse::getBytecode);
    }
}
