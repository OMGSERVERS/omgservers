package com.omgservers.service.module.client.impl.service.clientService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.module.client.DeleteClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.DeleteClientMatchmakerRefResponse;
import com.omgservers.schema.module.client.DeleteClientMessagesRequest;
import com.omgservers.schema.module.client.DeleteClientMessagesResponse;
import com.omgservers.schema.module.client.DeleteClientRequest;
import com.omgservers.schema.module.client.DeleteClientResponse;
import com.omgservers.schema.module.client.DeleteClientRuntimeRefRequest;
import com.omgservers.schema.module.client.DeleteClientRuntimeRefResponse;
import com.omgservers.schema.module.client.FindClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.FindClientMatchmakerRefResponse;
import com.omgservers.schema.module.client.FindClientRuntimeRefRequest;
import com.omgservers.schema.module.client.FindClientRuntimeRefResponse;
import com.omgservers.schema.module.client.GetClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.GetClientMatchmakerRefResponse;
import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import com.omgservers.schema.module.client.GetClientRuntimeRefRequest;
import com.omgservers.schema.module.client.GetClientRuntimeRefResponse;
import com.omgservers.schema.module.client.InterchangeRequest;
import com.omgservers.schema.module.client.InterchangeResponse;
import com.omgservers.schema.module.client.SyncClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.SyncClientMatchmakerRefResponse;
import com.omgservers.schema.module.client.SyncClientMessageRequest;
import com.omgservers.schema.module.client.SyncClientMessageResponse;
import com.omgservers.schema.module.client.SyncClientRequest;
import com.omgservers.schema.module.client.SyncClientResponse;
import com.omgservers.schema.module.client.SyncClientRuntimeRefRequest;
import com.omgservers.schema.module.client.SyncClientRuntimeRefResponse;
import com.omgservers.schema.module.client.ViewClientMatchmakerRefsRequest;
import com.omgservers.schema.module.client.ViewClientMatchmakerRefsResponse;
import com.omgservers.schema.module.client.ViewClientMessagesRequest;
import com.omgservers.schema.module.client.ViewClientMessagesResponse;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsRequest;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsResponse;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.client.impl.operation.getClientModuleClient.ClientModuleClient;
import com.omgservers.service.module.client.impl.operation.getClientModuleClient.GetClientModuleClientOperation;
import com.omgservers.service.module.client.impl.service.clientService.ClientService;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.client.deleteClient.DeleteClientMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.client.getClient.GetClientMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.client.syncClient.SyncClientMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMatchmakerRef.deleteClientMatchmakerRef.DeleteClientMatchmakerRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMatchmakerRef.findClientMatchmakerRef.FindClientMatchmakerRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMatchmakerRef.getClientMatchmakerRef.GetClientMatchmakerRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMatchmakerRef.syncClientMatchmakerRef.SyncClientMatchmakerRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMatchmakerRef.viewClientMatchmakerRefs.ViewClientMatchmakerRefsMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMessage.deleteClientMessages.DeleteClientMessagesMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMessage.interchange.InterchangeMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMessage.syncClientMessage.SyncClientMessageMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMessage.viewClientMessages.ViewClientMessagesMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.deleteClientRuntimeRef.DeleteClientRuntimeRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.findClientRuntimeRef.FindClientRuntimeRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.getClientRuntimeRef.GetClientRuntimeRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.syncClientRuntimeRef.SyncClientRuntimeRefMethod;
import com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.viewClientRuntimeRefs.ViewClientRuntimeRefsMethod;
import com.omgservers.service.operation.server.CalculateShardOperation;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
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
    final HandleShardedRequestOperation handleShardedRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    @Override
    public Uni<GetClientResponse> getClient(@Valid final GetClientRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::getClient,
                getClientMethod::getClient);
    }

    @Override
    public Uni<SyncClientResponse> syncClient(@Valid final SyncClientRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::syncClient,
                syncClientMethod::syncClient);
    }

    @Override
    public Uni<DeleteClientResponse> deleteClient(@Valid final DeleteClientRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::deleteClient,
                deleteClientMethod::deleteClient);
    }

    @Override
    public Uni<InterchangeResponse> interchange(@Valid final InterchangeRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::interchange,
                interchangeMethod::interchange);
    }

    @Override
    public Uni<ViewClientMessagesResponse> viewClientMessages(
            @Valid final ViewClientMessagesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::viewClientMessages,
                viewClientMessagesMethod::viewClientMessages);
    }

    @Override
    public Uni<SyncClientMessageResponse> syncClientMessage(@Valid final SyncClientMessageRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::syncClientMessage,
                syncClientMessageMethod::syncClientMessage);
    }

    @Override
    public Uni<SyncClientMessageResponse> syncClientMessageWithIdempotency(
            @Valid final SyncClientMessageRequest request) {
        return syncClientMessage(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}",
                                    request.getClientMessage(), t.getMessage());
                            return Uni.createFrom().item(new SyncClientMessageResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteClientMessagesResponse> deleteClientMessages(
            @Valid final DeleteClientMessagesRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::deleteClientMessages,
                deleteClientMessagesMethod::deleteClientMessages);
    }

    @Override
    public Uni<GetClientRuntimeRefResponse> getClientRuntimeRef(@Valid final GetClientRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::getClientRuntimeRef,
                getClientRuntimeRefMethod::getClientRuntimeRef);
    }

    @Override
    public Uni<FindClientRuntimeRefResponse> findClientRuntimeRef(@Valid final FindClientRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::findClientRuntimeRef,
                findClientRuntimeRefMethod::findClientRuntimeRef);
    }

    @Override
    public Uni<ViewClientRuntimeRefsResponse> viewClientRuntimeRefs(@Valid final ViewClientRuntimeRefsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::viewClientRuntimeRefs,
                viewClientRuntimeRefsMethod::viewClientRuntimeRefs);
    }

    @Override
    public Uni<SyncClientRuntimeRefResponse> syncClientRuntimeRef(@Valid final SyncClientRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::syncClientRuntimeRef,
                syncClientRuntimeRefMethod::syncClientRuntimeRef);
    }

    @Override
    public Uni<DeleteClientRuntimeRefResponse> deleteClientRuntimeRef(
            @Valid final DeleteClientRuntimeRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::deleteClientRuntimeRef,
                deleteClientRuntimeRefMethod::deleteClientRuntimeRef);
    }

    @Override
    public Uni<GetClientMatchmakerRefResponse> getClientMatchmakerRef(
            @Valid final GetClientMatchmakerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::getClientMatchmakerRef,
                getClientMatchmakerRefMethod::getClientMatchmakerRef);
    }

    @Override
    public Uni<FindClientMatchmakerRefResponse> findClientMatchmakerRef(
            @Valid final FindClientMatchmakerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::findClientMatchmakerRef,
                findClientMatchmakerRefMethod::findClientMatchmakerRef);
    }

    @Override
    public Uni<ViewClientMatchmakerRefsResponse> viewClientMatchmakerRefs(
            @Valid final ViewClientMatchmakerRefsRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::viewClientMatchmakerRefs,
                viewClientMatchmakerRefsMethod::viewClientMatchmakerRefs);
    }

    @Override
    public Uni<SyncClientMatchmakerRefResponse> syncClientMatchmakerRef(
            @Valid final SyncClientMatchmakerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::syncClientMatchmakerRef,
                syncClientMatchmakerRefMethod::syncClientMatchmakerRef);
    }

    @Override
    public Uni<DeleteClientMatchmakerRefResponse> deleteClientMatchmakerRef(
            @Valid final DeleteClientMatchmakerRefRequest request) {
        return handleShardedRequestOperation.handleShardedRequest(log, request,
                getClientModuleClientOperation::getClient,
                ClientModuleClient::deleteClientMatchmakerRef,
                deleteClientMatchmakerRefMethod::deleteClientMatchmakerRef);
    }
}
