package com.omgservers.service.module.runtime.impl.service.doService.impl;

import com.omgservers.model.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.model.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.model.dto.runtime.DoKickClientRequest;
import com.omgservers.model.dto.runtime.DoKickClientResponse;
import com.omgservers.model.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.model.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.model.dto.runtime.DoRespondClientRequest;
import com.omgservers.model.dto.runtime.DoRespondClientResponse;
import com.omgservers.model.dto.runtime.DoSetAttributesRequest;
import com.omgservers.model.dto.runtime.DoSetAttributesResponse;
import com.omgservers.model.dto.runtime.DoSetProfileRequest;
import com.omgservers.model.dto.runtime.DoSetProfileResponse;
import com.omgservers.model.dto.runtime.DoStopMatchmakingRequest;
import com.omgservers.model.dto.runtime.DoStopMatchmakingResponse;
import com.omgservers.service.module.runtime.impl.operation.getRuntimeModuleClient.GetRuntimeModuleClientOperation;
import com.omgservers.service.module.runtime.impl.operation.getRuntimeModuleClient.RuntimeModuleClient;
import com.omgservers.service.module.runtime.impl.service.doService.DoService;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doBroadcastMessage.DoBroadcastMessageMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doKickClient.DoKickClientMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doMulticastMessage.DoMulticastMessageMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doRespondClient.DoRespondClientMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doSetAttributes.DoSetAttributesMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doSetProfile.DoSetProfileMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doStopMatchmaking.DoStopMatchmakingMethod;
import com.omgservers.service.operation.handleInternalRequest.HandleInternalRequestOperation;
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
    final DoRespondClientMethod doRespondClientMethod;
    final DoSetAttributesMethod doSetAttributesMethod;
    final DoStopMatchmakingMethod doStopMatchmakingMethod;
    final DoKickClientMethod doKickClientMethod;
    final DoSetProfileMethod doSetProfileMethod;

    @Override
    public Uni<DoRespondClientResponse> doRespondClient(@Valid final DoRespondClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doRespondClient,
                doRespondClientMethod::doRespondClient);
    }

    @Override
    public Uni<DoSetAttributesResponse> doSetAttributes(@Valid final DoSetAttributesRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doSetAttributes,
                doSetAttributesMethod::doSetAttributes);
    }

    @Override
    public Uni<DoSetProfileResponse> doSetProfile(@Valid final DoSetProfileRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doSetProfile,
                doSetProfileMethod::doSetProfile);
    }

    @Override
    public Uni<DoKickClientResponse> doKickClient(@Valid final DoKickClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doKickClient,
                doKickClientMethod::doKickClient);
    }

    @Override
    public Uni<DoStopMatchmakingResponse> doStopRuntime(@Valid final DoStopMatchmakingRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doStopRuntime,
                doStopMatchmakingMethod::doStopMatchmaking);
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
