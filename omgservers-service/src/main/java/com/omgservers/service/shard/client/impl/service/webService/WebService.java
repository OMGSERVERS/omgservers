package com.omgservers.service.shard.client.impl.service.webService;

import com.omgservers.schema.module.client.client.*;
import com.omgservers.schema.module.client.clientMessage.*;
import com.omgservers.schema.module.client.clientRuntimeRef.*;
import io.smallrye.mutiny.Uni;

public interface WebService {

    /*
    Client
     */

    Uni<GetClientResponse> execute(GetClientRequest request);

    Uni<SyncClientResponse> execute(SyncClientRequest request);

    Uni<DeleteClientResponse> execute(DeleteClientRequest request);

    /*
    ClientMessages
     */

    Uni<ViewClientMessagesResponse> execute(ViewClientMessagesRequest request);

    Uni<SyncClientMessageResponse> execute(SyncClientMessageRequest request);

    Uni<DeleteClientMessagesResponse> execute(DeleteClientMessagesRequest request);

    Uni<InterchangeMessagesResponse> execute(InterchangeMessagesRequest request);

    /*
    ClientRuntimeRef
     */

    Uni<GetClientRuntimeRefResponse> execute(GetClientRuntimeRefRequest request);

    Uni<FindClientRuntimeRefResponse> execute(FindClientRuntimeRefRequest request);

    Uni<ViewClientRuntimeRefsResponse> execute(ViewClientRuntimeRefsRequest request);

    Uni<SyncClientRuntimeRefResponse> execute(SyncClientRuntimeRefRequest request);

    Uni<DeleteClientRuntimeRefResponse> execute(DeleteClientRuntimeRefRequest request);
}
