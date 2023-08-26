package com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.impl;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.RuntimeInternalService;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.RuntimeWebService;
import com.omgservers.dto.runtimeModule.DeleteCommandShardRequest;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalResponse;
import com.omgservers.dto.runtimeModule.DeleteRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.DoUpdateShardRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.SyncCommandShardRequest;
import com.omgservers.dto.runtimeModule.SyncCommandInternalResponse;
import com.omgservers.dto.runtimeModule.SyncRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class RuntimeWebServiceImpl implements RuntimeWebService {

    final RuntimeInternalService runtimeInternalService;

    @Override
    public Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeShardRequest request) {
        return runtimeInternalService.syncRuntime(request);
    }

    @Override
    public Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeShardRequest request) {
        return runtimeInternalService.getRuntime(request);
    }

    @Override
    public Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeShardRequest request) {
        return runtimeInternalService.deleteRuntime(request);
    }

    @Override
    public Uni<SyncCommandInternalResponse> syncCommand(SyncCommandShardRequest request) {
        return runtimeInternalService.syncCommand(request);
    }

    @Override
    public Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandShardRequest request) {
        return runtimeInternalService.deleteCommand(request);
    }

    @Override
    public Uni<Void> doUpdate(DoUpdateShardRequest request) {
        return runtimeInternalService.doUpdate(request);
    }
}
