package com.omgservers.module.runtime.impl.service.doService.impl;

import com.omgservers.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.dto.runtime.DoKickClientRequest;
import com.omgservers.dto.runtime.DoKickClientResponse;
import com.omgservers.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.dto.runtime.DoStopRuntimeRequest;
import com.omgservers.dto.runtime.DoStopRuntimeResponse;
import com.omgservers.dto.runtime.DoUnicastMessageRequest;
import com.omgservers.dto.runtime.DoUnicastMessageResponse;
import com.omgservers.module.runtime.impl.operation.getRuntimeModuleClient.GetRuntimeModuleClientOperation;
import com.omgservers.module.runtime.impl.operation.getRuntimeModuleClient.RuntimeModuleClient;
import com.omgservers.module.runtime.impl.service.doService.DoService;
import com.omgservers.module.runtime.impl.service.doService.impl.method.doBroadcastMessage.DoBroadcastMessageMethod;
import com.omgservers.module.runtime.impl.service.doService.impl.method.doKickClient.DoKickClientMethod;
import com.omgservers.module.runtime.impl.service.doService.impl.method.doMulticastMessage.DoMulticastMessageMethod;
import com.omgservers.module.runtime.impl.service.doService.impl.method.doStopRuntime.DoStopRuntimeMethod;
import com.omgservers.module.runtime.impl.service.doService.impl.method.doUnicastMessage.DoUnicastMessageMethod;
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
public class DoServiceImpl implements DoService {

    final GetRuntimeModuleClientOperation getRuntimeModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;

    final DoMulticastMessageMethod doMulticastMessageMethod;
    final DoBroadcastMessageMethod doBroadcastMessageMethod;
    final DoUnicastMessageMethod doUnicastMessageMethod;
    final DoStopRuntimeMethod doStopRuntimeMethod;
    final DoKickClientMethod doKickClientMethod;

    @Override
    public Uni<DoKickClientResponse> doKickClient(@Valid final DoKickClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doKickClient,
                doKickClientMethod::doKickClient);
    }

    @Override
    public Uni<DoStopRuntimeResponse> doStopRuntime(@Valid final DoStopRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doStopRuntime,
                doStopRuntimeMethod::doStopRuntime);
    }

    @Override
    public Uni<DoUnicastMessageResponse> doUnicastMessage(@Valid final DoUnicastMessageRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doUnicastMessage,
                doUnicastMessageMethod::doUnicastMessage);
    }

    @Override
    public Uni<DoMulticastMessageResponse> doMulticastMessage(@Valid final DoMulticastMessageRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doMulticastMessage,
                doMulticastMessageMethod::doMulticastMessage);
    }

    @Override
    public Uni<DoBroadcastMessageResponse> doBroadcastMessage(@Valid final DoBroadcastMessageRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doBroadcastMessage,
                doBroadcastMessageMethod::doBroadcastMessage);
    }
}