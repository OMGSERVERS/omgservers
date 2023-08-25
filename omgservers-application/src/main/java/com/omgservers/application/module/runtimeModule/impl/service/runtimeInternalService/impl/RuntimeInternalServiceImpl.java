package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl;

import com.omgservers.application.module.runtimeModule.impl.operation.getRuntimeServiceApiClientOperation.GetRuntimeServiceApiClientOperation;
import com.omgservers.application.module.runtimeModule.impl.operation.getRuntimeServiceApiClientOperation.RuntimeServiceApiClient;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.RuntimeInternalService;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteCommandMethod.DeleteCommandMethod;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.deleteRuntimeMethod.DeleteRuntimeMethod;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.doUpdateMethod.DoUpdateMethod;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.getRuntimeMethod.GetRuntimeMethod;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncCommandMethod.SyncCommandMethod;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.syncRuntimeMethod.SyncRuntimeMethod;
import com.omgservers.base.impl.operation.handleInternalRequestOperation.HandleInternalRequestOperation;
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
public class RuntimeInternalServiceImpl implements RuntimeInternalService {

    final GetRuntimeServiceApiClientOperation getRuntimeServiceApiClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    final DeleteRuntimeMethod deleteRuntimeMethod;
    final DeleteCommandMethod deleteCommandMethod;
    final SyncRuntimeMethod syncRuntimeMethod;
    final SyncCommandMethod syncCommandMethod;
    final GetRuntimeMethod getRuntimeMethod;
    final DoUpdateMethod doUpdateMethod;

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
    public Uni<SyncCommandInternalResponse> syncCommand(SyncCommandInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncCommandInternalRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::syncCommand,
                syncCommandMethod::syncCommand);
    }

    @Override
    public Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteCommandInternalRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::deleteCommand,
                deleteCommandMethod::deleteCommand);
    }

    @Override
    public Uni<Void> doUpdate(DoUpdateInternalRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DoUpdateInternalRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::doUpdate,
                doUpdateMethod::doUpdate);
    }
}
