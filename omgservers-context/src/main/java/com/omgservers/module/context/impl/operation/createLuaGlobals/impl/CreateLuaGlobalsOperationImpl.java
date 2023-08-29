package com.omgservers.module.context.impl.operation.createLuaGlobals.impl;

import com.omgservers.dto.tenant.GetStageShardedResponse;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.version.GetBytecodeShardedRequest;
import com.omgservers.dto.version.GetBytecodeShardedResponse;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.version.VersionBytecodeModel;
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
    public Uni<LuaGlobals> createLuaGlobals(final Long tenantId, final Long stageId) {
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId is null");
        }
        if (stageId == null) {
            throw new IllegalArgumentException("stageId is null");
        }

        // TODO: Use version from runtime
        return getStageVersion(tenantId, stageId)
                .flatMap(this::getBytecode)
                .map(versionBytecode -> {
                    final var base64Files = versionBytecode.getFiles();
                    log.info("Create lua runtime, countOfFiles={}", base64Files.size());
                    final var luaBytecode = decodeLuaBytecodeOperation.decodeLuaBytecode(base64Files);
                    // TODO: use user globals?
                    final var globals = createServerGlobalsOperation.createServerGlobals();
                    globals.finder = new LuaResourceFinder(luaBytecode);
                    final var luaRuntime = new LuaGlobals(globals);
                    // TODO: use param for main file
                    luaRuntime.getGlobals().get("dofile").call(LuaValue.valueOf("main.lua"));
                    return luaRuntime;
                })
                .invoke(luaGlobals -> log.info("Lua runtime was created, {}", luaGlobals));
    }

    Uni<Long> getStageVersion(final Long tenantId, final Long stageId) {
        final var request = new GetStageShardedRequest(tenantId, stageId);
        return tenantModule.getStageShardedService().getStage(request)
                .map(GetStageShardedResponse::getStage)
                .map(StageModel::getVersionId);
    }

    Uni<VersionBytecodeModel> getBytecode(final Long id) {
        final var request = new GetBytecodeShardedRequest(id);
        return versionModule.getVersionShardedService().getBytecode(request)
                .map(GetBytecodeShardedResponse::getBytecode);
    }
}
