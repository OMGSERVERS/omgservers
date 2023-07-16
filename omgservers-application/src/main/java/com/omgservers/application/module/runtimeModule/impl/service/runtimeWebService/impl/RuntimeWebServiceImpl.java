package com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.impl;

import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.RuntimeInternalService;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.CreateRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.GetRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteRuntimeInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.GetRuntimeInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeWebService.RuntimeWebService;
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
    public Uni<Void> createRuntime(CreateRuntimeInternalRequest request) {
        return runtimeInternalService.createRuntime(request);
    }

    @Override
    public Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeInternalRequest request) {
        return runtimeInternalService.getRuntime(request);
    }

    @Override
    public Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeInternalRequest request) {
        return runtimeInternalService.deleteRuntime(request);
    }
}
