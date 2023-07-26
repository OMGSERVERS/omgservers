package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.method.createLuaRuntimeMethod;

import com.omgservers.application.module.luaModule.impl.operation.createServerGlobalsOperation.CreateServerGlobalsOperation;
import com.omgservers.application.module.luaModule.impl.operation.createUserGlobalsOperation.CreateUserGlobalsOperation;
import com.omgservers.application.module.luaModule.impl.operation.decodeLuaBytecodeOperation.DecodeLuaBytecodeOperation;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.LuaRuntime;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreateLuaRuntimeHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreateLuaRuntimeHelpResponse;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.versionModule.VersionModule;
import com.omgservers.application.module.tenantModule.model.stage.StageModel;
import com.omgservers.application.module.versionModule.model.VersionBytecodeModel;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.GetStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.GetStageInternalResponse;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.GetBytecodeInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetBytecodeInternalResponse;
import io.smallrye.mutiny.Uni;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;
import org.luaj.vm2.LuaValue;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLuaRuntimeMethodImpl implements CreateLuaRuntimeMethod {

    final VersionModule versionModule;
    final TenantModule tenantModule;

    final CreateServerGlobalsOperation createServerGlobalsOperation;
    final DecodeLuaBytecodeOperation decodeLuaBytecodeOperation;
    final CreateUserGlobalsOperation createUserGlobalsOperation;

    @Override
    public Uni<CreateLuaRuntimeHelpResponse> createLuaRuntime(final CreateLuaRuntimeHelpRequest request) {
        CreateLuaRuntimeHelpRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();

        return getStageVersion(tenantId, stageId)
                .flatMap(this::getBytecode)
                .map(versionBytecode -> {
                    final var base64Files = versionBytecode.getFiles();
                    log.info("Create lua runtime, countOfFiles={}", base64Files.size());
                    final var luaBytecode = decodeLuaBytecodeOperation.decodeLuaBytecode(base64Files);
                    final var globals = createServerGlobalsOperation.createServerGlobals();
                    globals.finder = new LuaResourceFinder(luaBytecode);
                    final var luaRuntime = new LuaRuntime(globals);
                    luaRuntime.getGlobals().get("dofile").call(LuaValue.valueOf("main.lua"));
                    return luaRuntime;
                })
                .invoke(luaRuntime -> log.info("Lua runtime was created, {}", luaRuntime))
                .map(CreateLuaRuntimeHelpResponse::new);
    }

    Uni<Long> getStageVersion(final Long tenantId, final Long stageId) {
        final var request = new GetStageInternalRequest(tenantId, stageId);
        return tenantModule.getStageInternalService().getStage(request)
                .map(GetStageInternalResponse::getStage)
                .map(StageModel::getVersionId);
    }

    Uni<VersionBytecodeModel> getBytecode(final Long id) {
        final var request = new GetBytecodeInternalRequest(id);
        return versionModule.getVersionInternalService().getBytecode(request)
                .map(GetBytecodeInternalResponse::getBytecode);
    }
}
