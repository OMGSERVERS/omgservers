package com.omgservers.service.module.client.impl.service.webService.impl.api;

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
import com.omgservers.service.module.client.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
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
