package com.omgservers.service.module.client.impl.service.webService.impl;

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
import com.omgservers.service.module.client.impl.service.clientService.ClientService;
import com.omgservers.service.module.client.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class WebServiceImpl implements WebService {

    final ClientService clientService;

    @Override
    public Uni<GetClientResponse> getClient(final GetClientRequest request) {
        return clientService.getClient(request);
    }

    @Override
    public Uni<SyncClientResponse> syncClient(final SyncClientRequest request) {
        return clientService.syncClient(request);
    }

    @Override
    public Uni<DeleteClientResponse> deleteClient(final DeleteClientRequest request) {
        return clientService.deleteClient(request);
    }

    @Override
    public Uni<InterchangeResponse> interchange(final InterchangeRequest request) {
        return clientService.interchange(request);
    }

    @Override
    public Uni<ViewClientMessagesResponse> viewClientMessages(final ViewClientMessagesRequest request) {
        return clientService.viewClientMessages(request);
    }

    @Override
    public Uni<SyncClientMessageResponse> syncClientMessage(final SyncClientMessageRequest request) {
        return clientService.syncClientMessage(request);
    }

    @Override
    public Uni<DeleteClientMessagesResponse> deleteClientMessages(
            final DeleteClientMessagesRequest request) {
        return clientService.deleteClientMessages(request);
    }

    @Override
    public Uni<GetClientRuntimeRefResponse> getClientRuntimeRef(final GetClientRuntimeRefRequest request) {
        return clientService.getClientRuntimeRef(request);
    }

    @Override
    public Uni<FindClientRuntimeRefResponse> findClientRuntimeRef(final FindClientRuntimeRefRequest request) {
        return clientService.findClientRuntimeRef(request);
    }

    @Override
    public Uni<ViewClientRuntimeRefsResponse> viewClientRuntimeRefs(final ViewClientRuntimeRefsRequest request) {
        return clientService.viewClientRuntimeRefs(request);
    }

    @Override
    public Uni<SyncClientRuntimeRefResponse> syncClientRuntimeRef(final SyncClientRuntimeRefRequest request) {
        return clientService.syncClientRuntimeRef(request);
    }

    @Override
    public Uni<DeleteClientRuntimeRefResponse> deleteClientRuntimeRef(final DeleteClientRuntimeRefRequest request) {
        return clientService.deleteClientRuntimeRef(request);
    }

    @Override
    public Uni<GetClientMatchmakerRefResponse> getClientMatchmakerRef(final GetClientMatchmakerRefRequest request) {
        return clientService.getClientMatchmakerRef(request);
    }

    @Override
    public Uni<FindClientMatchmakerRefResponse> findClientMatchmakerRef(final FindClientMatchmakerRefRequest request) {
        return clientService.findClientMatchmakerRef(request);
    }

    @Override
    public Uni<ViewClientMatchmakerRefsResponse> viewClientMatchmakerRefs(
            final ViewClientMatchmakerRefsRequest request) {
        return clientService.viewClientMatchmakerRefs(request);
    }

    @Override
    public Uni<SyncClientMatchmakerRefResponse> syncClientMatchmakerRef(final SyncClientMatchmakerRefRequest request) {
        return clientService.syncClientMatchmakerRef(request);
    }

    @Override
    public Uni<DeleteClientMatchmakerRefResponse> deleteClientMatchmakerRef(
            final DeleteClientMatchmakerRefRequest request) {
        return clientService.deleteClientMatchmakerRef(request);
    }
}
