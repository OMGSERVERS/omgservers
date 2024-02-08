package com.omgservers.service.module.client.impl.service.webService;

import com.omgservers.model.dto.client.DeleteClientMessagesRequest;
import com.omgservers.model.dto.client.DeleteClientMessagesResponse;
import com.omgservers.model.dto.client.DeleteClientRequest;
import com.omgservers.model.dto.client.DeleteClientResponse;
import com.omgservers.model.dto.client.DeleteClientRuntimeRequest;
import com.omgservers.model.dto.client.DeleteClientRuntimeResponse;
import com.omgservers.model.dto.client.FindClientRuntimeRequest;
import com.omgservers.model.dto.client.FindClientRuntimeResponse;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.GetClientRuntimeRequest;
import com.omgservers.model.dto.client.GetClientRuntimeResponse;
import com.omgservers.model.dto.client.InterchangeRequest;
import com.omgservers.model.dto.client.InterchangeResponse;
import com.omgservers.model.dto.client.SelectClientRuntimeRequest;
import com.omgservers.model.dto.client.SelectClientRuntimeResponse;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.dto.client.SyncClientRequest;
import com.omgservers.model.dto.client.SyncClientResponse;
import com.omgservers.model.dto.client.SyncClientRuntimeRequest;
import com.omgservers.model.dto.client.SyncClientRuntimeResponse;
import com.omgservers.model.dto.client.ViewClientMessagesRequest;
import com.omgservers.model.dto.client.ViewClientMessagesResponse;
import com.omgservers.model.dto.client.ViewClientRuntimesRequest;
import com.omgservers.model.dto.client.ViewClientRuntimesResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetClientResponse> getClient(GetClientRequest request);

    Uni<SyncClientResponse> syncClient(SyncClientRequest request);

    Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request);

    Uni<InterchangeResponse> interchange(InterchangeRequest request);

    Uni<ViewClientMessagesResponse> viewClientMessages(ViewClientMessagesRequest request);

    Uni<SyncClientMessageResponse> syncClientMessage(SyncClientMessageRequest request);

    Uni<DeleteClientMessagesResponse> deleteClientMessages(DeleteClientMessagesRequest request);

    Uni<GetClientRuntimeResponse> getClientRuntime(GetClientRuntimeRequest request);

    Uni<FindClientRuntimeResponse> findClientRuntime(FindClientRuntimeRequest request);

    Uni<ViewClientRuntimesResponse> viewClientRuntimes(ViewClientRuntimesRequest request);

    Uni<SelectClientRuntimeResponse> selectClientRuntime(SelectClientRuntimeRequest request);

    Uni<SyncClientRuntimeResponse> syncClientRuntime(SyncClientRuntimeRequest request);

    Uni<DeleteClientRuntimeResponse> deleteClientRuntime(DeleteClientRuntimeRequest request);
}
