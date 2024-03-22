package com.omgservers.service.module.client.impl.service.clientService.impl;

import com.omgservers.model.dto.client.DeleteClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.DeleteClientMatchmakerRefResponse;
import com.omgservers.model.dto.client.DeleteClientMessagesRequest;
import com.omgservers.model.dto.client.DeleteClientMessagesResponse;
import com.omgservers.model.dto.client.DeleteClientRequest;
import com.omgservers.model.dto.client.DeleteClientResponse;
import com.omgservers.model.dto.client.DeleteClientRuntimeRefRequest;
import com.omgservers.model.dto.client.DeleteClientRuntimeRefResponse;
import com.omgservers.model.dto.client.FindClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.FindClientMatchmakerRefResponse;
import com.omgservers.model.dto.client.FindClientRuntimeRefRequest;
import com.omgservers.model.dto.client.FindClientRuntimeRefResponse;
import com.omgservers.model.dto.client.GetClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.GetClientMatchmakerRefResponse;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.GetClientRuntimeRefRequest;
import com.omgservers.model.dto.client.GetClientRuntimeRefResponse;
import com.omgservers.model.dto.client.InterchangeRequest;
import com.omgservers.model.dto.client.InterchangeResponse;
import com.omgservers.model.dto.client.SyncClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.SyncClientMatchmakerRefResponse;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.dto.client.SyncClientRequest;
import com.omgservers.model.dto.client.SyncClientResponse;
import com.omgservers.model.dto.client.SyncClientRuntimeRefRequest;
import com.omgservers.model.dto.client.SyncClientRuntimeRefResponse;
import com.omgservers.model.dto.client.ViewClientMatchmakerRefsRequest;
import com.omgservers.model.dto.client.ViewClientMatchmakerRefsResponse;
import com.omgservers.model.dto.client.ViewClientMessagesRequest;
import com.omgservers.model.dto.client.ViewClientMessagesResponse;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsRequest;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsResponse;
import com.omgservers.service.module.client.impl.operation.getClientModuleClient.ClientModuleClient;
import com.omgservers.service.module.client.impl.operation.getClientModuleClient.GetClientModuleClientOperation;
import com.omgservers.service.module.client.impl.service.clientService.ClientService;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.deleteClient.DeleteClientMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.deleteClientMatchmakerRef.DeleteClientMatchmakerRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.deleteClientMessages.DeleteClientMessagesMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.deleteClientRuntimeRef.DeleteClientRuntimeRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.findClientMatchmakerRef.FindClientMatchmakerRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.findClientRuntimeRef.FindClientRuntimeRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.getClient.GetClientMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.getClientMatchmakerRef.GetClientMatchmakerRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.getClientRuntimeRef.GetClientRuntimeRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.interchange.InterchangeMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.syncClient.SyncClientMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.syncClientMatchmakerRef.SyncClientMatchmakerRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.syncClientMessage.SyncClientMessageMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.syncClientRuntimeRef.SyncClientRuntimeRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.viewClientMatchmakerRefs.ViewClientMatchmakerRefsMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.viewClientMessages.ViewClientMessagesMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.viewClientRuntimeRefs.ViewClientRuntimeRefsMethod;
import com.omgservers.service.operation.calculateShard.CalculateShardOperation;
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
public class ClientServiceImpl implements ClientService {

    final DeleteClientMatchmakerRefMethod deleteClientMatchmakerRefMethod;
    final ViewClientMatchmakerRefsMethod viewClientMatchmakerRefsMethod;
    final FindClientMatchmakerRefMethod findClientMatchmakerRefMethod;
    final SyncClientMatchmakerRefMethod syncClientMatchmakerRefMethod;
    final DeleteClientRuntimeRefMethod deleteClientRuntimeRefMethod;
    final GetClientMatchmakerRefMethod getClientMatchmakerRefMethod;
    final ViewClientRuntimeRefsMethod viewClientRuntimeRefsMethod;
    final DeleteClientMessagesMethod deleteClientMessagesMethod;
    final SyncClientRuntimeRefMethod syncClientRuntimeRefMethod;
    final FindClientRuntimeRefMethod findClientRuntimeRefMethod;
    final GetClientRuntimeRefMethod getClientRuntimeRefMethod;
    final ViewClientMessagesMethod viewClientMessagesMethod;
    final SyncClientMessageMethod syncClientMessageMethod;
    final DeleteClientMethod deleteClientMethod;
    final InterchangeMethod interchangeMethod;
    final SyncClientMethod syncClientMethod;
    final GetClientMethod getClientMethod;

    final GetClientModuleClientOperation getClientModuleClientOperation;
    final HandleInternalRequestOperation handleInternalRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetClientResponse> getClient(@Valid final GetClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::getClient,
                getClientMethod::getClient);
    }

    @Override
    public Uni<SyncClientResponse> syncClient(@Valid final SyncClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::syncClient,
                syncClientMethod::syncClient);
    }

    @Override
    public Uni<DeleteClientResponse> deleteClient(@Valid final DeleteClientRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::deleteClient,
                deleteClientMethod::deleteClient);
    }

    @Override
    public Uni<InterchangeResponse> interchange(@Valid final InterchangeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::interchange,
                interchangeMethod::interchange);
    }

    @Override
    public Uni<ViewClientMessagesResponse> viewClientMessages(
            @Valid final ViewClientMessagesRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::viewClientMessages,
                viewClientMessagesMethod::viewClientMessages);
    }

    @Override
    public Uni<SyncClientMessageResponse> syncClientMessage(@Valid final SyncClientMessageRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::syncClientMessage,
                syncClientMessageMethod::syncClientMessage);
    }

    @Override
    public Uni<DeleteClientMessagesResponse> deleteClientMessages(
            @Valid final DeleteClientMessagesRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::deleteClientMessages,
                deleteClientMessagesMethod::deleteClientMessages);
    }

    @Override
    public Uni<GetClientRuntimeRefResponse> getClientRuntimeRef(@Valid final GetClientRuntimeRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::getClientRuntimeRef,
                getClientRuntimeRefMethod::getClientRuntimeRef);
    }

    @Override
    public Uni<FindClientRuntimeRefResponse> findClientRuntimeRef(@Valid final FindClientRuntimeRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::findClientRuntimeRef,
                findClientRuntimeRefMethod::findClientRuntimeRef);
    }

    @Override
    public Uni<ViewClientRuntimeRefsResponse> viewClientRuntimeRefs(@Valid final ViewClientRuntimeRefsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::viewClientRuntimeRefs,
                viewClientRuntimeRefsMethod::viewClientRuntimeRefs);
    }

    @Override
    public Uni<SyncClientRuntimeRefResponse> syncClientRuntimeRef(@Valid final SyncClientRuntimeRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::syncClientRuntimeRef,
                syncClientRuntimeRefMethod::syncClientRuntimeRef);
    }

    @Override
    public Uni<DeleteClientRuntimeRefResponse> deleteClientRuntimeRef(
            @Valid final DeleteClientRuntimeRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::deleteClientRuntimeRef,
                deleteClientRuntimeRefMethod::deleteClientRuntimeRef);
    }

    @Override
    public Uni<GetClientMatchmakerRefResponse> getClientMatchmakerRef(
            @Valid final GetClientMatchmakerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::getClientMatchmakerRef,
                getClientMatchmakerRefMethod::getClientMatchmakerRef);
    }

    @Override
    public Uni<FindClientMatchmakerRefResponse> findClientMatchmakerRef(
            @Valid final FindClientMatchmakerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::findClientMatchmakerRef,
                findClientMatchmakerRefMethod::findClientMatchmakerRef);
    }

    @Override
    public Uni<ViewClientMatchmakerRefsResponse> viewClientMatchmakerRefs(
            @Valid final ViewClientMatchmakerRefsRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::viewClientMatchmakerRefs,
                viewClientMatchmakerRefsMethod::viewClientMatchmakerRefs);
    }

    @Override
    public Uni<SyncClientMatchmakerRefResponse> syncClientMatchmakerRef(
            @Valid final SyncClientMatchmakerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::syncClientMatchmakerRef,
                syncClientMatchmakerRefMethod::syncClientMatchmakerRef);
    }

    @Override
    public Uni<DeleteClientMatchmakerRefResponse> deleteClientMatchmakerRef(
            @Valid final DeleteClientMatchmakerRefRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::deleteClientMatchmakerRef,
                deleteClientMatchmakerRefMethod::deleteClientMatchmakerRef);
    }
}
