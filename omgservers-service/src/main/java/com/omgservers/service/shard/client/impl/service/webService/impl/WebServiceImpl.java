package com.omgservers.service.shard.client.impl.service.webService.impl;

import com.omgservers.schema.module.client.client.*;
import com.omgservers.schema.module.client.clientMessage.*;
import com.omgservers.schema.module.client.clientRuntimeRef.*;
import com.omgservers.service.shard.client.impl.service.clientService.ClientService;
import com.omgservers.service.shard.client.impl.service.webService.WebService;
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

    /*
    Client
     */

    @Override
    public Uni<GetClientResponse> execute(final GetClientRequest request) {
        return clientService.execute(request);
    }

    @Override
    public Uni<SyncClientResponse> execute(final SyncClientRequest request) {
        return clientService.execute(request);
    }

    @Override
    public Uni<DeleteClientResponse> execute(final DeleteClientRequest request) {
        return clientService.execute(request);
    }

    /*
    ClientMessages
     */

    @Override
    public Uni<ViewClientMessagesResponse> execute(final ViewClientMessagesRequest request) {
        return clientService.execute(request);
    }

    @Override
    public Uni<SyncClientMessageResponse> execute(final SyncClientMessageRequest request) {
        return clientService.execute(request);
    }

    @Override
    public Uni<DeleteClientMessagesResponse> execute(final DeleteClientMessagesRequest request) {
        return clientService.execute(request);
    }

    @Override
    public Uni<InterchangeMessagesResponse> execute(final InterchangeMessagesRequest request) {
        return clientService.execute(request);
    }

    /*
    ClientRuntimeRef
     */

    @Override
    public Uni<GetClientRuntimeRefResponse> execute(final GetClientRuntimeRefRequest request) {
        return clientService.execute(request);
    }

    @Override
    public Uni<FindClientRuntimeRefResponse> execute(final FindClientRuntimeRefRequest request) {
        return clientService.execute(request);
    }

    @Override
    public Uni<ViewClientRuntimeRefsResponse> execute(final ViewClientRuntimeRefsRequest request) {
        return clientService.execute(request);
    }

    @Override
    public Uni<SyncClientRuntimeRefResponse> execute(final SyncClientRuntimeRefRequest request) {
        return clientService.execute(request);
    }

    @Override
    public Uni<DeleteClientRuntimeRefResponse> execute(final DeleteClientRuntimeRefRequest request) {
        return clientService.execute(request);
    }
}
