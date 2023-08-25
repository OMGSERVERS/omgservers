package com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.impl;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.RuntimeInternalService;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.RuntimeWebService;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalRequest;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalResponse;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalRequest;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.DoUpdateInternalRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.SyncCommandInternalRequest;
import com.omgservers.dto.runtimeModule.SyncCommandInternalResponse;
import com.omgservers.dto.runtimeModule.SyncRuntimeInternalRequest;
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
    public Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeInternalRequest request) {
        return runtimeInternalService.syncRuntime(request);
    }

    @Override
    public Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeInternalRequest request) {
        return runtimeInternalService.getRuntime(request);
    }

    @Override
    public Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeInternalRequest request) {
        return runtimeInternalService.deleteRuntime(request);
    }

    @Override
    public Uni<SyncCommandInternalResponse> syncCommand(SyncCommandInternalRequest request) {
        return runtimeInternalService.syncCommand(request);
    }

    @Override
    public Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandInternalRequest request) {
        return runtimeInternalService.deleteCommand(request);
    }

    @Override
    public Uni<Void> doUpdate(DoUpdateInternalRequest request) {
        return runtimeInternalService.doUpdate(request);
    }
}
