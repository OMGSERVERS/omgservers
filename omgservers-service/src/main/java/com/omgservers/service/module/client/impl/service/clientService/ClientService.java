package com.omgservers.service.module.client.impl.service.clientService;

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
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ClientService {

    Uni<GetClientResponse> getClient(@Valid GetClientRequest request);

    Uni<SyncClientResponse> syncClient(@Valid SyncClientRequest request);

    Uni<DeleteClientResponse> deleteClient(@Valid DeleteClientRequest request);

    Uni<InterchangeResponse> interchange(@Valid InterchangeRequest request);

    Uni<ViewClientMessagesResponse> viewClientMessages(@Valid ViewClientMessagesRequest request);

    Uni<SyncClientMessageResponse> syncClientMessage(@Valid SyncClientMessageRequest request);

    Uni<SyncClientMessageResponse> syncClientMessageWithIdempotency(@Valid SyncClientMessageRequest request);

    Uni<DeleteClientMessagesResponse> deleteClientMessages(@Valid DeleteClientMessagesRequest request);

    Uni<GetClientRuntimeRefResponse> getClientRuntimeRef(@Valid GetClientRuntimeRefRequest request);

    Uni<FindClientRuntimeRefResponse> findClientRuntimeRef(@Valid FindClientRuntimeRefRequest request);

    Uni<ViewClientRuntimeRefsResponse> viewClientRuntimeRefs(@Valid ViewClientRuntimeRefsRequest request);

    Uni<SyncClientRuntimeRefResponse> syncClientRuntimeRef(@Valid SyncClientRuntimeRefRequest request);

    Uni<DeleteClientRuntimeRefResponse> deleteClientRuntimeRef(@Valid DeleteClientRuntimeRefRequest request);

    Uni<GetClientMatchmakerRefResponse> getClientMatchmakerRef(@Valid GetClientMatchmakerRefRequest request);

    Uni<FindClientMatchmakerRefResponse> findClientMatchmakerRef(@Valid FindClientMatchmakerRefRequest request);

    Uni<ViewClientMatchmakerRefsResponse> viewClientMatchmakerRefs(@Valid ViewClientMatchmakerRefsRequest request);

    Uni<SyncClientMatchmakerRefResponse> syncClientMatchmakerRef(@Valid SyncClientMatchmakerRefRequest request);

    Uni<DeleteClientMatchmakerRefResponse> deleteClientMatchmakerRef(@Valid DeleteClientMatchmakerRefRequest request);
}
