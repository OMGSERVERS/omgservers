package com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.impl;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.RuntimeInternalService;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.RuntimeWebService;
import com.omgservers.dto.runtimeModule.DeleteCommandRoutedRequest;
import com.omgservers.dto.runtimeModule.DeleteCommandInternalResponse;
import com.omgservers.dto.runtimeModule.DeleteRuntimeRoutedRequest;
import com.omgservers.dto.runtimeModule.DeleteRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.DoUpdateRoutedRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeRoutedRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
import com.omgservers.dto.runtimeModule.SyncCommandRoutedRequest;
import com.omgservers.dto.runtimeModule.SyncCommandInternalResponse;
import com.omgservers.dto.runtimeModule.SyncRuntimeRoutedRequest;
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
    public Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeRoutedRequest request) {
        return runtimeInternalService.syncRuntime(request);
    }

    @Override
    public Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeRoutedRequest request) {
        return runtimeInternalService.getRuntime(request);
    }

    @Override
    public Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeRoutedRequest request) {
        return runtimeInternalService.deleteRuntime(request);
    }

    @Override
    public Uni<SyncCommandInternalResponse> syncCommand(SyncCommandRoutedRequest request) {
        return runtimeInternalService.syncCommand(request);
    }

    @Override
    public Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandRoutedRequest request) {
        return runtimeInternalService.deleteCommand(request);
    }

    @Override
    public Uni<Void> doUpdate(DoUpdateRoutedRequest request) {
        return runtimeInternalService.doUpdate(request);
    }
}
