package com.omgservers.module.runtime.impl.service.runtimeService.impl;

import com.omgservers.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.dto.runtime.DoRuntimeUpdateRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateResponse;
import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.dto.runtime.SyncRuntimeRequest;
import com.omgservers.dto.runtime.SyncRuntimeResponse;
import com.omgservers.module.runtime.impl.operation.getRuntimeModuleClient.GetRuntimeModuleClientOperation;
import com.omgservers.module.runtime.impl.operation.getRuntimeModuleClient.RuntimeModuleClient;
import com.omgservers.module.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntime.DeleteRuntimeMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeCommand.DeleteRuntimeCommandMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.doRuntimeUpdate.DoRuntimeUpdateMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.getRuntime.GetRuntimeMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntime.SyncRuntimeMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeCommand.SyncRuntimeCommandMethod;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeServiceImpl implements RuntimeService {

    final GetRuntimeModuleClientOperation getRuntimeModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    final DeleteRuntimeCommandMethod deleteRuntimeCommandMethod;
    final SyncRuntimeCommandMethod syncRuntimeCommandMethod;
    final DoRuntimeUpdateMethod doRuntimeUpdateMethod;
    final DeleteRuntimeMethod deleteRuntimeMethod;
    final SyncRuntimeMethod syncRuntimeMethod;
    final GetRuntimeMethod getRuntimeMethod;

    @Override
    public Uni<SyncRuntimeResponse> syncRuntime(SyncRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncRuntimeRequest::validate,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntime,
                syncRuntimeMethod::syncRuntime);
    }

    @Override
    public Uni<GetRuntimeResponse> getRuntime(GetRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetRuntimeRequest::validate,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::getRuntime,
                getRuntimeMethod::getRuntime);
    }

    @Override
    public Uni<DeleteRuntimeResponse> deleteRuntime(DeleteRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteRuntimeRequest::validate,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntime,
                deleteRuntimeMethod::deleteRuntime);
    }

    @Override
    public Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(SyncRuntimeCommandRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncRuntimeCommandRequest::validate,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimeCommand,
                syncRuntimeCommandMethod::syncRuntimeCommand);
    }

    @Override
    public Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(DeleteRuntimeCommandRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteRuntimeCommandRequest::validate,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimeCommand,
                deleteRuntimeCommandMethod::deleteRuntimeCommand);
    }

    @Override
    public Uni<DoRuntimeUpdateResponse> doRuntimeUpdate(DoRuntimeUpdateRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DoRuntimeUpdateRequest::validate,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doRuntimeUpdate,
                doRuntimeUpdateMethod::doRuntimeUpdate);
    }
}
