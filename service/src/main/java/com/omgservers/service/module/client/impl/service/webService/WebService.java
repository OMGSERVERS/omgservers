package com.omgservers.service.module.client.impl.service.webService;

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

public interface WebService {

    Uni<GetClientResponse> getClient(GetClientRequest request);

    Uni<SyncClientResponse> syncClient(SyncClientRequest request);

    Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request);

    Uni<InterchangeResponse> interchange(InterchangeRequest request);

    Uni<ViewClientMessagesResponse> viewClientMessages(ViewClientMessagesRequest request);

    Uni<SyncClientMessageResponse> syncClientMessage(SyncClientMessageRequest request);

    Uni<DeleteClientMessagesResponse> deleteClientMessages(DeleteClientMessagesRequest request);

    Uni<GetClientRuntimeRefResponse> getClientRuntimeRef(GetClientRuntimeRefRequest request);

    Uni<FindClientRuntimeRefResponse> findClientRuntimeRef(FindClientRuntimeRefRequest request);

    Uni<ViewClientRuntimeRefsResponse> viewClientRuntimeRefs(ViewClientRuntimeRefsRequest request);

    Uni<SyncClientRuntimeRefResponse> syncClientRuntimeRef(SyncClientRuntimeRefRequest request);

    Uni<DeleteClientRuntimeRefResponse> deleteClientRuntimeRef(DeleteClientRuntimeRefRequest request);

    Uni<GetClientMatchmakerRefResponse> getClientMatchmakerRef(GetClientMatchmakerRefRequest request);

    Uni<FindClientMatchmakerRefResponse> findClientMatchmakerRef(FindClientMatchmakerRefRequest request);

    Uni<ViewClientMatchmakerRefsResponse> viewClientMatchmakerRefs(ViewClientMatchmakerRefsRequest request);

    Uni<SyncClientMatchmakerRefResponse> syncClientMatchmakerRef(SyncClientMatchmakerRefRequest request);

    Uni<DeleteClientMatchmakerRefResponse> deleteClientMatchmakerRef(DeleteClientMatchmakerRefRequest request);
}
