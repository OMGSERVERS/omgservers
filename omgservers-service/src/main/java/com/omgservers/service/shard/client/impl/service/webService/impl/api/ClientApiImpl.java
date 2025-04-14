package com.omgservers.service.shard.client.impl.service.webService.impl.api;

import com.omgservers.schema.model.user.UserRoleEnum;
import com.omgservers.schema.shard.client.client.*;
import com.omgservers.schema.shard.client.clientMessage.*;
import com.omgservers.schema.shard.client.clientRuntimeRef.*;
import com.omgservers.service.operation.server.HandleApiRequestOperation;
import com.omgservers.service.shard.client.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({UserRoleEnum.Names.SERVICE})
class ClientApiImpl implements ClientApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    /*
    Client
     */

    @Override
    public Uni<GetClientResponse> execute(final GetClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncClientResponse> execute(final SyncClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteClientResponse> execute(final DeleteClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    ClientMessage
     */

    @Override
    public Uni<ViewClientMessagesResponse> execute(final ViewClientMessagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncClientMessageResponse> execute(final SyncClientMessageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteClientMessagesResponse> execute(final DeleteClientMessagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<InterchangeMessagesResponse> execute(final InterchangeMessagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    /*
    ClientRuntimeRef
     */

    @Override
    public Uni<GetClientRuntimeRefResponse> execute(final GetClientRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<FindClientRuntimeRefResponse> execute(final FindClientRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<ViewClientRuntimeRefsResponse> execute(final ViewClientRuntimeRefsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<SyncClientRuntimeRefResponse> execute(final SyncClientRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }

    @Override
    public Uni<DeleteClientRuntimeRefResponse> execute(final DeleteClientRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::execute);
    }
}
