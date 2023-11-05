package com.omgservers.service.module.runtime.impl.service.doService.impl;

import com.omgservers.model.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.model.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.model.dto.runtime.DoChangePlayerRequest;
import com.omgservers.model.dto.runtime.DoChangePlayerResponse;
import com.omgservers.model.dto.runtime.DoKickClientRequest;
import com.omgservers.model.dto.runtime.DoKickClientResponse;
import com.omgservers.model.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.model.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.model.dto.runtime.DoRespondClientRequest;
import com.omgservers.model.dto.runtime.DoRespondClientResponse;
import com.omgservers.model.dto.runtime.DoSetAttributesRequest;
import com.omgservers.model.dto.runtime.DoSetAttributesResponse;
import com.omgservers.model.dto.runtime.DoSetObjectRequest;
import com.omgservers.model.dto.runtime.DoSetObjectResponse;
import com.omgservers.model.dto.runtime.DoStopRuntimeRequest;
import com.omgservers.model.dto.runtime.DoStopRuntimeResponse;
import com.omgservers.model.dto.runtime.DoUnicastMessageRequest;
import com.omgservers.model.dto.runtime.DoUnicastMessageResponse;
import com.omgservers.service.module.runtime.impl.operation.getRuntimeModuleClient.GetRuntimeModuleClientOperation;
import com.omgservers.service.module.runtime.impl.operation.getRuntimeModuleClient.RuntimeModuleClient;
import com.omgservers.service.module.runtime.impl.service.doService.DoService;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doBroadcastMessage.DoBroadcastMessageMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doChangePlayer.DoChangePlayerMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doKickClient.DoKickClientMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doMulticastMessage.DoMulticastMessageMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doRespondClient.DoRespondClientMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doSetAttributes.DoSetAttributesMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doSetObject.DoSetObjectMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doStopRuntime.DoStopRuntimeMethod;
import com.omgservers.service.module.runtime.impl.service.doService.impl.method.doUnicastMessage.DoUnicastMessageMethod;
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
    final DoUnicastMessageMethod doUnicastMessageMethod;
    final DoRespondClientMethod doRespondClientMethod;
    final DoSetAttributesMethod doSetAttributesMethod;
    final DoChangePlayerMethod doChangePlayerMethod;
    final DoStopRuntimeMethod doStopRuntimeMethod;
    final DoKickClientMethod doKickClientMethod;
    final DoSetObjectMethod doSetObjectMethod;

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
    public Uni<DoSetObjectResponse> doSetObject(@Valid final DoSetObjectRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doSetObject,
                doSetObjectMethod::doSetObject);
    }

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
    public Uni<DoChangePlayerResponse> doChangePlayer(@Valid final DoChangePlayerRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getRuntimeModuleClientOperation::getClient,
                RuntimeModuleClient::doChangePlayer,
                doChangePlayerMethod::doChangePlayer);
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
