package com.omgservers.service.shard.client.impl.service.clientService;

import com.omgservers.schema.shard.client.client.*;
import com.omgservers.schema.shard.client.clientMessage.*;
import com.omgservers.schema.shard.client.clientRuntimeRef.*;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ClientService {

    /*
    Client
     */

    Uni<GetClientResponse> execute(@Valid GetClientRequest request);

    Uni<SyncClientResponse> execute(@Valid SyncClientRequest request);

    Uni<DeleteClientResponse> execute(@Valid DeleteClientRequest request);

    /*
    ClientMessage
     */

    Uni<ViewClientMessagesResponse> execute(@Valid ViewClientMessagesRequest request);

    Uni<SyncClientMessageResponse> execute(@Valid SyncClientMessageRequest request);

    Uni<SyncClientMessageResponse> executeWithIdempotency(@Valid SyncClientMessageRequest request);

    Uni<DeleteClientMessagesResponse> execute(@Valid DeleteClientMessagesRequest request);

    Uni<InterchangeMessagesResponse> execute(@Valid InterchangeMessagesRequest request);

    /*
    ClientRuntimeRef
     */

    Uni<GetClientRuntimeRefResponse> execute(@Valid GetClientRuntimeRefRequest request);

    Uni<FindClientRuntimeRefResponse> execute(@Valid FindClientRuntimeRefRequest request);

    Uni<ViewClientRuntimeRefsResponse> execute(@Valid ViewClientRuntimeRefsRequest request);

    Uni<SyncClientRuntimeRefResponse> execute(@Valid SyncClientRuntimeRefRequest request);

    Uni<SyncClientRuntimeRefResponse> executeWithIdempotency(@Valid SyncClientRuntimeRefRequest request);

    Uni<DeleteClientRuntimeRefResponse> execute(@Valid DeleteClientRuntimeRefRequest request);
}
