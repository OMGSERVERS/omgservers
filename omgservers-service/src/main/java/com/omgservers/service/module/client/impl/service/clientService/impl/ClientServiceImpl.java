package com.omgservers.service.module.client.impl.service.clientService.impl;

import com.omgservers.model.dto.client.DeleteClientMessagesRequest;
import com.omgservers.model.dto.client.DeleteClientMessagesResponse;
import com.omgservers.model.dto.client.DeleteClientRequest;
import com.omgservers.model.dto.client.DeleteClientResponse;
import com.omgservers.model.dto.client.DeleteClientRuntimeRequest;
import com.omgservers.model.dto.client.DeleteClientRuntimeResponse;
import com.omgservers.model.dto.client.FindClientRuntimeRequest;
import com.omgservers.model.dto.client.FindClientRuntimeResponse;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.GetClientRuntimeRequest;
import com.omgservers.model.dto.client.GetClientRuntimeResponse;
import com.omgservers.model.dto.client.HandleMessageRequest;
import com.omgservers.model.dto.client.HandleMessageResponse;
import com.omgservers.model.dto.client.ReceiveMessagesRequest;
import com.omgservers.model.dto.client.ReceiveMessagesResponse;
import com.omgservers.model.dto.client.SelectClientRuntimeRequest;
import com.omgservers.model.dto.client.SelectClientRuntimeResponse;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.dto.client.SyncClientRequest;
import com.omgservers.model.dto.client.SyncClientResponse;
import com.omgservers.model.dto.client.SyncClientRuntimeRequest;
import com.omgservers.model.dto.client.SyncClientRuntimeResponse;
import com.omgservers.model.dto.client.ViewClientMessagesRequest;
import com.omgservers.model.dto.client.ViewClientMessagesResponse;
import com.omgservers.model.dto.client.ViewClientRuntimesRequest;
import com.omgservers.model.dto.client.ViewClientRuntimesResponse;
import com.omgservers.service.module.client.impl.operation.getClientModuleClient.ClientModuleClient;
import com.omgservers.service.module.client.impl.operation.getClientModuleClient.GetClientModuleClientOperation;
import com.omgservers.service.module.client.impl.service.clientService.ClientService;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.deleteClient.DeleteClientMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.deleteClientMessages.DeleteClientMessagesMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.deleteClientRuntime.DeleteClientRuntimeMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.findClientRuntime.FindClientRuntimeMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.getClient.GetClientMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.getClientRuntime.GetClientRuntimeMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.handleMessage.HandleMessageMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.receiveMessages.ReceiveMessagesMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.selectClientRuntime.SelectClientRuntimeMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.syncClient.SyncClientMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.syncClientMessage.SyncClientMessageMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.syncClientRuntime.SyncClientRuntimeMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.viewClientMessages.ViewClientMessagesMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.viewClientRuntimes.ViewClientRuntimesMethod;
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

    final DeleteClientMessagesMethod deleteClientMessagesMethod;
    final SelectClientRuntimeMethod selectClientRuntimeMethod;
    final DeleteClientRuntimeMethod deleteClientRuntimeMethod;
    final ViewClientMessagesMethod viewClientMessagesMethod;
    final ViewClientRuntimesMethod viewClientRuntimesMethod;
    final SyncClientMessageMethod syncClientMessageMethod;
    final SyncClientRuntimeMethod syncClientRuntimeMethod;
    final FindClientRuntimeMethod findClientRuntimeMethod;
    final GetClientRuntimeMethod getClientRuntimeMethod;
    final ReceiveMessagesMethod receiveMessagesMethod;
    final DeleteClientMethod deleteClientMethod;
    final HandleMessageMethod handleMessage;
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
    public Uni<HandleMessageResponse> handleMessage(@Valid final HandleMessageRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::handleMessage,
                handleMessage::handleMessage);
    }

    @Override
    public Uni<ReceiveMessagesResponse> receiveMessages(@Valid final ReceiveMessagesRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::receiveMessages,
                receiveMessagesMethod::receiveMessages);
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
    public Uni<GetClientRuntimeResponse> getClientRuntime(@Valid final GetClientRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::getClientRuntime,
                getClientRuntimeMethod::getClientRuntime);
    }

    @Override
    public Uni<FindClientRuntimeResponse> findClientRuntime(@Valid final FindClientRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::findClientRuntime,
                findClientRuntimeMethod::findClientRuntime);
    }

    @Override
    public Uni<ViewClientRuntimesResponse> viewClientRuntimes(@Valid final ViewClientRuntimesRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::viewClientRuntimes,
                viewClientRuntimesMethod::viewClientRuntimes);
    }

    @Override
    public Uni<SelectClientRuntimeResponse> selectClientRuntime(@Valid final SelectClientRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::selectClientRuntime,
                selectClientRuntimeMethod::selectClientRuntime);
    }

    @Override
    public Uni<SyncClientRuntimeResponse> syncClientRuntime(@Valid final SyncClientRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::syncClientRuntime,
                syncClientRuntimeMethod::syncClientRuntime);
    }

    @Override
    public Uni<DeleteClientRuntimeResponse> deleteClientRuntime(@Valid final DeleteClientRuntimeRequest request) {
        return handleInternalRequestOperation.handleInternalRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::deleteClientRuntime,
                deleteClientRuntimeMethod::deleteClientRuntime);
    }
}
