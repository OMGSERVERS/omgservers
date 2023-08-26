package com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.method.createLuaRuntimeMethod;

import com.omgservers.application.module.luaModule.impl.operation.createServerGlobalsOperation.CreateServerGlobalsOperation;
import com.omgservers.application.module.luaModule.impl.operation.createUserGlobalsOperation.CreateUserGlobalsOperation;
import com.omgservers.application.module.luaModule.impl.operation.decodeLuaBytecodeOperation.DecodeLuaBytecodeOperation;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.LuaRuntime;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreateLuaRuntimeHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreateLuaRuntimeHelpResponse;
import com.omgservers.application.module.tenantModule.TenantModule;
import com.omgservers.application.module.versionModule.VersionModule;
import com.omgservers.dto.tenantModule.GetStageRoutedRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.versionModule.GetBytecodeRoutedRequest;
import com.omgservers.dto.versionModule.GetBytecodeInternalResponse;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.version.VersionBytecodeModel;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaValue;

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
        final var request = new GetStageRoutedRequest(tenantId, stageId);
        return tenantModule.getStageInternalService().getStage(request)
                .map(GetStageInternalResponse::getStage)
                .map(StageModel::getVersionId);
    }

    Uni<VersionBytecodeModel> getBytecode(final Long id) {
        final var request = new GetBytecodeRoutedRequest(id);
        return versionModule.getVersionInternalService().getBytecode(request)
                .map(GetBytecodeInternalResponse::getBytecode);
    }
}
