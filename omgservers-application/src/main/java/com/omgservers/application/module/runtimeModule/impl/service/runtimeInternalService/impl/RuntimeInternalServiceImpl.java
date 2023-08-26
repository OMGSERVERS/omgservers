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
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
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
    public Uni<SyncRuntimeInternalResponse> syncRuntime(SyncRuntimeShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncRuntimeShardRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::syncRuntime,
                syncRuntimeMethod::syncRuntime);
    }

    @Override
    public Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetRuntimeShardRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::getRuntime,
                getRuntimeMethod::getRuntime);
    }

    @Override
    public Uni<DeleteRuntimeInternalResponse> deleteRuntime(DeleteRuntimeShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteRuntimeShardRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::deleteRuntime,
                deleteRuntimeMethod::deleteRuntime);
    }

    @Override
    public Uni<SyncCommandInternalResponse> syncCommand(SyncCommandShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncCommandShardRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::syncCommand,
                syncCommandMethod::syncCommand);
    }

    @Override
    public Uni<DeleteCommandInternalResponse> deleteCommand(DeleteCommandShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteCommandShardRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::deleteCommand,
                deleteCommandMethod::deleteCommand);
    }

    @Override
    public Uni<Void> doUpdate(DoUpdateShardRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DoUpdateShardRequest::validate,
                getRuntimeServiceApiClientOperation::getClient,
                RuntimeServiceApiClient::doUpdate,
                doUpdateMethod::doUpdate);
    }
}
