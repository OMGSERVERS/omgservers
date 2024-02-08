package com.omgservers.service.module.client.impl.service.clientService;

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
import jakarta.validation.Valid;

public interface ClientService {

    Uni<GetClientResponse> getClient(@Valid GetClientRequest request);

    Uni<SyncClientResponse> syncClient(@Valid SyncClientRequest request);

    Uni<DeleteClientResponse> deleteClient(@Valid DeleteClientRequest request);

    Uni<InterchangeResponse> interchange(@Valid InterchangeRequest request);

    Uni<ViewClientMessagesResponse> viewClientMessages(@Valid ViewClientMessagesRequest request);

    Uni<SyncClientMessageResponse> syncClientMessage(@Valid SyncClientMessageRequest request);

    Uni<DeleteClientMessagesResponse> deleteClientMessages(@Valid DeleteClientMessagesRequest request);

    Uni<GetClientRuntimeResponse> getClientRuntime(@Valid GetClientRuntimeRequest request);

    Uni<FindClientRuntimeResponse> findClientRuntime(@Valid FindClientRuntimeRequest request);

    Uni<ViewClientRuntimesResponse> viewClientRuntimes(@Valid ViewClientRuntimesRequest request);

    Uni<SelectClientRuntimeResponse> selectClientRuntime(@Valid SelectClientRuntimeRequest request);

    Uni<SyncClientRuntimeResponse> syncClientRuntime(@Valid SyncClientRuntimeRequest request);

    Uni<DeleteClientRuntimeResponse> deleteClientRuntime(@Valid DeleteClientRuntimeRequest request);
}
