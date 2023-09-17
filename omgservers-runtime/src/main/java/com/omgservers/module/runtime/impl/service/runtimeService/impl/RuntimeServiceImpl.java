package com.omgservers.module.runtime.impl.service.runtimeService.impl;

import com.omgservers.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.dto.runtime.DeleteRuntimeGrantResponse;
import com.omgservers.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.dto.runtime.GetRuntimeRequest;
import com.omgservers.dto.runtime.GetRuntimeResponse;
import com.omgservers.dto.runtime.MarkRuntimeCommandsRequest;
import com.omgservers.dto.runtime.MarkRuntimeCommandsResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.dto.runtime.SyncRuntimeRequest;
import com.omgservers.dto.runtime.SyncRuntimeResponse;
import com.omgservers.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.module.runtime.impl.operation.getRuntimeModuleClient.GetRuntimeModuleClientOperation;
import com.omgservers.module.runtime.impl.operation.getRuntimeModuleClient.RuntimeModuleClient;
import com.omgservers.module.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntime.DeleteRuntimeMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeCommand.DeleteRuntimeCommandMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeGrant.DeleteRuntimeGrantMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.getRuntime.GetRuntimeMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.markRuntimeCommands.MarkRuntimeCommandsMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntime.SyncRuntimeMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeCommand.SyncRuntimeCommandMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntimeGrant.SyncRuntimeGrantMethod;
import com.omgservers.module.runtime.impl.service.runtimeService.impl.method.viewRuntimeCommands.ViewRuntimeCommandsMethod;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
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
    final MarkRuntimeCommandsMethod markRuntimeCommandsMethod;
    final ViewRuntimeCommandsMethod viewRuntimeCommandsMethod;
    final SyncRuntimeCommandMethod syncRuntimeCommandMethod;
    final DeleteRuntimeGrantMethod deleteRuntimeGrantMethod;
    final SyncRuntimeGrantMethod syncRuntimeGrantMethod;
    final DeleteRuntimeMethod deleteRuntimeMethod;
    final SyncRuntimeMethod syncRuntimeMethod;
    final GetRuntimeMethod getRuntimeMethod;

    @Override
    public Uni<SyncRuntimeResponse> syncRuntime(@Valid final SyncRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntime,
                syncRuntimeMethod::syncRuntime);
    }

    @Override
    public Uni<GetRuntimeResponse> getRuntime(@Valid final GetRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::getRuntime,
                getRuntimeMethod::getRuntime);
    }

    @Override
    public Uni<DeleteRuntimeResponse> deleteRuntime(@Valid final DeleteRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntime,
                deleteRuntimeMethod::deleteRuntime);
    }

    @Override
    public Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(@Valid final SyncRuntimeCommandRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimeCommand,
                syncRuntimeCommandMethod::syncRuntimeCommand);
    }

    @Override
    public Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(@Valid final DeleteRuntimeCommandRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimeCommand,
                deleteRuntimeCommandMethod::deleteRuntimeCommand);
    }

    @Override
    public Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(@Valid final ViewRuntimeCommandsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::viewRuntimeCommands,
                viewRuntimeCommandsMethod::viewRuntimeCommands);
    }

    @Override
    public Uni<MarkRuntimeCommandsResponse> markRuntimeCommands(@Valid final MarkRuntimeCommandsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::markRuntimeCommands,
                markRuntimeCommandsMethod::markRuntimeCommands);
    }

    @Override
    public Uni<SyncRuntimeGrantResponse> syncRuntimeGrant(@Valid final SyncRuntimeGrantRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimeGrant,
                syncRuntimeGrantMethod::syncRuntimeGrant);
    }

    @Override
    public Uni<DeleteRuntimeGrantResponse> deleteRuntimeGrant(@Valid final DeleteRuntimeGrantRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimeGrant,
                deleteRuntimeGrantMethod::deleteRuntimeGrant);
    }
}
