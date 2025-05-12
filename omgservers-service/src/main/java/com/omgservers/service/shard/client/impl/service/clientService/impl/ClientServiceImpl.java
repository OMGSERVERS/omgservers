package com.omgservers.service.shard.client.impl.service.clientService.impl;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.shard.client.client.*;
import com.omgservers.schema.shard.client.clientMessage.*;
import com.omgservers.schema.shard.client.clientRuntimeRef.*;
import com.omgservers.service.exception.ServerSideBaseException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.CalculateShardOperation;
import com.omgservers.service.operation.server.HandleShardedRequestOperation;
import com.omgservers.service.shard.client.impl.operation.getClientModuleClient.ClientModuleClient;
import com.omgservers.service.shard.client.impl.operation.getClientModuleClient.GetClientModuleClientOperation;
import com.omgservers.service.shard.client.impl.service.clientService.ClientService;
import com.omgservers.service.shard.client.impl.service.clientService.impl.method.client.DeleteClientMethod;
import com.omgservers.service.shard.client.impl.service.clientService.impl.method.client.GetClientMethod;
import com.omgservers.service.shard.client.impl.service.clientService.impl.method.client.SyncClientMethod;
import com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage.DeleteClientMessagesMethod;
import com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage.InterchangeMessagesMethod;
import com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage.SyncClientMessageMethod;
import com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage.ViewClientMessagesMethod;
import com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef.*;
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

    final DeleteClientRuntimeRefMethod deleteClientRuntimeRefMethod;
    final ViewClientRuntimeRefsMethod viewClientRuntimeRefsMethod;
    final DeleteClientMessagesMethod deleteClientMessagesMethod;
    final SyncClientRuntimeRefMethod syncClientRuntimeRefMethod;
    final FindClientRuntimeRefMethod findClientRuntimeRefMethod;
    final InterchangeMessagesMethod interchangeMessagesMethod;
    final GetClientRuntimeRefMethod getClientRuntimeRefMethod;
    final ViewClientMessagesMethod viewClientMessagesMethod;
    final SyncClientMessageMethod syncClientMessageMethod;
    final DeleteClientMethod deleteClientMethod;
    final SyncClientMethod syncClientMethod;
    final GetClientMethod getClientMethod;

    final GetClientModuleClientOperation getClientModuleClientOperation;
    final HandleShardedRequestOperation handleShardedRequestOperation;
    final CalculateShardOperation calculateShardOperation;

    /*
    Client
     */

    @Override
    public Uni<GetClientResponse> execute(@Valid final GetClientRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getClientModuleClientOperation::execute,
                ClientModuleClient::execute,
                getClientMethod::execute);
    }

    @Override
    public Uni<SyncClientResponse> execute(@Valid final SyncClientRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getClientModuleClientOperation::execute,
                ClientModuleClient::execute,
                syncClientMethod::execute);
    }

    @Override
    public Uni<DeleteClientResponse> execute(@Valid final DeleteClientRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getClientModuleClientOperation::execute,
                ClientModuleClient::execute,
                deleteClientMethod::execute);
    }

    /*
    ClientMessages
     */

    @Override
    public Uni<ViewClientMessagesResponse> execute(
            @Valid final ViewClientMessagesRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getClientModuleClientOperation::execute,
                ClientModuleClient::execute,
                viewClientMessagesMethod::execute);
    }

    @Override
    public Uni<SyncClientMessageResponse> execute(@Valid final SyncClientMessageRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getClientModuleClientOperation::execute,
                ClientModuleClient::execute,
                syncClientMessageMethod::execute);
    }

    @Override
    public Uni<SyncClientMessageResponse> executeWithIdempotency(
            @Valid final SyncClientMessageRequest request) {
        return execute(request)
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
    public Uni<DeleteClientMessagesResponse> execute(
            @Valid final DeleteClientMessagesRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getClientModuleClientOperation::execute,
                ClientModuleClient::execute,
                deleteClientMessagesMethod::execute);
    }

    @Override
    public Uni<InterchangeMessagesResponse> execute(@Valid final InterchangeMessagesRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getClientModuleClientOperation::execute,
                ClientModuleClient::execute,
                interchangeMessagesMethod::execute);
    }

    /*
    ClientRuntimeRef
     */

    @Override
    public Uni<GetClientRuntimeRefResponse> execute(@Valid final GetClientRuntimeRefRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getClientModuleClientOperation::execute,
                ClientModuleClient::execute,
                getClientRuntimeRefMethod::execute);
    }

    @Override
    public Uni<FindClientRuntimeRefResponse> execute(@Valid final FindClientRuntimeRefRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getClientModuleClientOperation::execute,
                ClientModuleClient::execute,
                findClientRuntimeRefMethod::execute);
    }

    @Override
    public Uni<ViewClientRuntimeRefsResponse> execute(@Valid final ViewClientRuntimeRefsRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getClientModuleClientOperation::execute,
                ClientModuleClient::execute,
                viewClientRuntimeRefsMethod::execute);
    }

    @Override
    public Uni<SyncClientRuntimeRefResponse> execute(@Valid final SyncClientRuntimeRefRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getClientModuleClientOperation::execute,
                ClientModuleClient::execute,
                syncClientRuntimeRefMethod::execute);
    }

    @Override
    public Uni<SyncClientRuntimeRefResponse> executeWithIdempotency(@Valid final SyncClientRuntimeRefRequest request) {
        return execute(request)
                .onFailure(ServerSideConflictException.class)
                .recoverWithUni(t -> {
                    if (t instanceof final ServerSideBaseException exception) {
                        if (exception.getQualifier().equals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED)) {
                            log.debug("Idempotency was violated, object={}, {}",
                                    request.getClientRuntimeRef(), t.getMessage());
                            return Uni.createFrom().item(new SyncClientRuntimeRefResponse(Boolean.FALSE));
                        }
                    }

                    return Uni.createFrom().failure(t);
                });
    }

    @Override
    public Uni<DeleteClientRuntimeRefResponse> execute(
            @Valid final DeleteClientRuntimeRefRequest request) {
        return handleShardedRequestOperation.execute(log, request,
                getClientModuleClientOperation::execute,
                ClientModuleClient::execute,
                deleteClientRuntimeRefMethod::execute);
    }
}
