package com.omgservers.module.context.impl.operation.createLuaGlobals.impl;

import com.omgservers.dto.version.GetVersionShardedRequest;
import com.omgservers.dto.version.GetVersionShardedResponse;
import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.model.version.VersionModel;
import com.omgservers.model.version.VersionStatusEnum;
import com.omgservers.module.context.impl.operation.createLuaGlobals.CreateLuaGlobalsOperation;
import com.omgservers.module.context.impl.operation.decodeLuaBytecode.DecodeLuaBytecodeOperation;
import com.omgservers.module.tenant.TenantModule;
import com.omgservers.module.version.VersionModule;
import com.omgservers.operation.createServerGlobals.CreateServerGlobalsOperation;
import com.omgservers.operation.createUserGlobals.CreateUserGlobalsOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaValue;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLuaGlobalsOperationImpl implements CreateLuaGlobalsOperation {

    final VersionModule versionModule;
    final TenantModule tenantModule;

    final DecodeLuaBytecodeOperation decodeLuaBytecodeOperation;
    final CreateUserGlobalsOperation createUserGlobalsOperation;
    final CreateServerGlobalsOperation createServerGlobalsOperation;

    @Override
    public Uni<LuaGlobals> createLuaGlobals(final Long versionId) {
        if (versionId == null) {
            throw new IllegalArgumentException("versionId is null");
        }

        // TODO: Use version from runtime
        return getVersion(versionId)
                .map(version -> {
                    if (version.getStatus() != VersionStatusEnum.DEPLOYED) {
                        throw new ServerSideConflictException("Version wasn't deployed yet, version=" + version);
                    }
                    return version.getBytecode();
                })
                .map(versionBytecode -> {
                    final var base64Files = versionBytecode.getFiles();
                    log.info("Create lua runtime, countOfFiles={}", base64Files.size());
                    final var luaBytecode = decodeLuaBytecodeOperation.decodeLuaBytecode(base64Files);
                    // TODO: use user globals?
                    final var globals = createServerGlobalsOperation.createServerGlobals();
                    globals.finder = new LuaResourceFinder(luaBytecode);
                    final var luaGlobals = new LuaGlobals(globals);
                    // TODO: use param for main file
                    luaGlobals.getGlobals().get("dofile").call(LuaValue.valueOf("main.lua"));
                    return luaGlobals;
                })
                .invoke(luaGlobals -> log.info("Lua runtime was created, {}", luaGlobals));
    }

    Uni<VersionModel> getVersion(final Long versionId) {
        final var request = new GetVersionShardedRequest(versionId);
        return versionModule.getVersionShardedService().getVersion(request)
                .map(GetVersionShardedResponse::getVersion);
    }
}
