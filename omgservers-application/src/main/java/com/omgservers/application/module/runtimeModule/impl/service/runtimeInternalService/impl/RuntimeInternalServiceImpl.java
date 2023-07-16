package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl;

import com.omgservers.application.module.runtimeModule.impl.operation.getRuntimeServiceApiClientOperation.GetRuntimeServiceApiClientOperation;
import com.omgservers.application.module.runtimeModule.impl.operation.getRuntimeServiceApiClientOperation.RuntimeServiceApiClient;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.RuntimeInternalService;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.createRuntimeMethod.CreateRuntimeMethod;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteRuntimeMethod.DeleteRuntimeMethod;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.getRuntimeMethod.GetRuntimeMethod;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.CreateRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.GetRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteRuntimeInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.GetRuntimeInternalResponse;
import com.omgservers.application.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeInternalServiceImpl implements RuntimeInternalService {

    final GetRuntimeServiceApiClientOperation getRuntimeServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    final CreateRuntimeMethod createRuntimeMethod;
    final DeleteRuntimeMethod deleteRuntimeMethod;
    final GetRuntimeMethod getRuntimeMethod;

    @Override
    public Uni<Void> createRuntime(CreateRuntimeInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                CreateRuntimeInternalRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::createRuntime,
                createRuntimeMethod::createRuntime);
    }

    @Override
    public Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetRuntimeInternalRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::getRuntime,
                getRuntimeMethod::getRuntime);
    }

    @Override
    public Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteRuntimeInternalRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::deleteRuntime,
                deleteRuntimeMethod::deleteRuntime);
    }
}
