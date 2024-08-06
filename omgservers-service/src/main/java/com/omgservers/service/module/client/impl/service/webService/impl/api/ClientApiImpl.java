package com.omgservers.service.module.client.impl.service.webService.impl.api;

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
import com.omgservers.schema.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.client.impl.service.webService.WebService;
import com.omgservers.service.server.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
@RolesAllowed({InternalRoleEnum.Names.SERVICE})
class ClientApiImpl implements ClientApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    public Uni<GetClientResponse> getClient(final GetClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getClient);
    }

    @Override
    public Uni<SyncClientResponse> syncClient(final SyncClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncClient);
    }

    @Override
    public Uni<DeleteClientResponse> deleteClient(final DeleteClientRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteClient);
    }

    @Override
    public Uni<InterchangeResponse> interchange(final InterchangeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::interchange);
    }

    @Override
    public Uni<ViewClientMessagesResponse> viewClientMessages(final ViewClientMessagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewClientMessages);
    }

    @Override
    public Uni<SyncClientMessageResponse> syncClientMessage(final SyncClientMessageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncClientMessage);
    }

    @Override
    public Uni<DeleteClientMessagesResponse> deleteClientMessages(
            final DeleteClientMessagesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteClientMessages);
    }

    @Override
    public Uni<GetClientRuntimeRefResponse> getClientRuntimeRef(final GetClientRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getClientRuntimeRef);
    }

    @Override
    public Uni<FindClientRuntimeRefResponse> findClientRuntimeRef(final FindClientRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findClientRuntimeRef);
    }

    @Override
    public Uni<ViewClientRuntimeRefsResponse> viewClientRuntimeRefs(final ViewClientRuntimeRefsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewClientRuntimeRefs);
    }

    @Override
    public Uni<SyncClientRuntimeRefResponse> syncClientRuntimeRef(final SyncClientRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncClientRuntimeRef);
    }

    @Override
    public Uni<DeleteClientRuntimeRefResponse> deleteClientRuntimeRef(final DeleteClientRuntimeRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteClientRuntimeRef);
    }

    @Override
    public Uni<GetClientMatchmakerRefResponse> getClientMatchmakerRef(final GetClientMatchmakerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getClientMatchmakerRef);
    }

    @Override
    public Uni<FindClientMatchmakerRefResponse> findClientMatchmakerRef(final FindClientMatchmakerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findClientMatchmakerRef);
    }

    @Override
    public Uni<ViewClientMatchmakerRefsResponse> viewClientMatchmakerRefs(
            final ViewClientMatchmakerRefsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewClientMatchmakerRefs);
    }

    @Override
    public Uni<SyncClientMatchmakerRefResponse> syncClientMatchmakerRef(final SyncClientMatchmakerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncClientMatchmakerRef);
    }

    @Override
    public Uni<DeleteClientMatchmakerRefResponse> deleteClientMatchmakerRef(
            final DeleteClientMatchmakerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteClientMatchmakerRef);
    }
}
