package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl;

import com.omgservers.application.module.runtimeModule.impl.operation.getRuntimeServiceApiClientOperation.GetRuntimeServiceApiClientOperation;
import com.omgservers.application.module.runtimeModule.impl.operation.getRuntimeServiceApiClientOperation.RuntimeServiceApiClient;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.RuntimeInternalService;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteActorMethod.DeleteActorMethod;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteRuntimeMethod.DeleteRuntimeMethod;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.getRuntimeMethod.GetRuntimeMethod;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncActorMethod.SyncActorMethod;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncRuntimeMethod.SyncRuntimeMethod;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteActorInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteCommandInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DeleteRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.GetRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncActorInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncCommandInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.SyncRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteActorInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteCommandInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.DeleteRuntimeInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.GetRuntimeInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.SyncActorInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.SyncCommandInternalResponse;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.SyncRuntimeInternalResponse;
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

    final DeleteRuntimeMethod deleteRuntimeMethod;
    final SyncRuntimeMethod syncRuntimeMethod;
    final DeleteActorMethod deleteActorMethod;
    final GetRuntimeMethod getRuntimeMethod;
    final SyncActorMethod syncActorMethod;

    @Override
    public Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncRuntimeInternalRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::syncRuntime,
                syncRuntimeMethod::syncRuntime);
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

    @Override
    public Uni<SyncActorInternalResponse> syncActor(SyncActorInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncActorInternalRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::syncActor,
                syncActorMethod::syncActor);
    }

    @Override
    public Uni<DeleteActorInternalResponse> deleteActor(DeleteActorInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteActorInternalRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::deleteActor,
                deleteActorMethod::deleteActor);
    }

    @Override
    public Uni<SyncCommandInternalResponse> syncCommand(SyncCommandInternalRequest request) {
        return null;
    }

    @Override
    public Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandInternalRequest request) {
        return null;
    }
}
