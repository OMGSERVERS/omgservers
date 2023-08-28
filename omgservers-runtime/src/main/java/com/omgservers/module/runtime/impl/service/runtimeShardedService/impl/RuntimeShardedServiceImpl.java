package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl;

import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedResponse;
import com.omgservers.dto.runtime.DeleteRuntimeShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeShardedResponse;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedRequest;
import com.omgservers.dto.runtime.DoRuntimeUpdateShardedResponse;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeCommandShardedResponse;
import com.omgservers.dto.runtime.SyncRuntimeShardedRequest;
import com.omgservers.dto.runtime.SyncRuntimeShardedResponse;
import com.omgservers.module.runtime.impl.operation.getRuntimeModuleClient.GetRuntimeModuleClientOperation;
import com.omgservers.module.runtime.impl.operation.getRuntimeModuleClient.RuntimeModuleClient;
import com.omgservers.module.runtime.impl.service.runtimeShardedService.RuntimeShardedService;
import com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.deleteRuntime.DeleteRuntimeMethod;
import com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.deleteRuntimeCommand.DeleteRuntimeCommandMethod;
import com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.doRuntimeUpdate.DoRuntimeUpdateMethod;
import com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.getRuntime.GetRuntimeMethod;
import com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.syncRuntime.SyncRuntimeMethod;
import com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.syncRuntimeCommand.SyncRuntimeCommandMethod;
import com.omgservers.operation.handleInternalRequest.HandleInternalRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeShardedServiceImpl implements RuntimeShardedService {

    final GetRuntimeModuleClientOperation getRuntimeModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    final DeleteRuntimeCommandMethod deleteRuntimeCommandMethod;
    final SyncRuntimeCommandMethod syncRuntimeCommandMethod;
    final DeleteRuntimeMethod deleteRuntimeMethod;
    final SyncRuntimeMethod syncRuntimeMethod;
    final GetRuntimeMethod getRuntimeMethod;
    final DoRuntimeUpdateMethod doRuntimeUpdateMethod;

    @Override
    public Uni<SyncRuntimeShardedResponse> syncRuntime(SyncRuntimeShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncRuntimeShardedRequest::validate,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntime,
                syncRuntimeMethod::syncRuntime);
    }

    @Override
    public Uni<GetRuntimeShardedResponse> getRuntime(GetRuntimeShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                GetRuntimeShardedRequest::validate,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::getRuntime,
                getRuntimeMethod::getRuntime);
    }

    @Override
    public Uni<DeleteRuntimeShardedResponse> deleteRuntime(DeleteRuntimeShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteRuntimeShardedRequest::validate,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntime,
                deleteRuntimeMethod::deleteRuntime);
    }

    @Override
    public Uni<SyncRuntimeCommandShardedResponse> syncRuntimeCommand(SyncRuntimeCommandShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                SyncRuntimeCommandShardedRequest::validate,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::syncRuntimeCommand,
                syncRuntimeCommandMethod::syncRuntimeCommand);
    }

    @Override
    public Uni<DeleteRuntimeCommandShardedResponse> deleteRuntimeCommand(DeleteRuntimeCommandShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DeleteRuntimeCommandShardedRequest::validate,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::deleteRuntimeCommand,
                deleteRuntimeCommandMethod::deleteRuntimeCommand);
    }

    @Override
    public Uni<DoRuntimeUpdateShardedResponse> doRuntimeUpdate(DoRuntimeUpdateShardedRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                DoRuntimeUpdateShardedRequest::validate,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doRuntimeUpdate,
                doRuntimeUpdateMethod::doRuntimeUpdate);
    }
}
