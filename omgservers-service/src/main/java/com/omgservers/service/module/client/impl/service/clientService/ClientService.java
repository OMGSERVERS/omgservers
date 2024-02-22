package com.omgservers.service.module.client.impl.service.clientService;

import com.omgservers.model.dto.client.DeleteClientMessagesRequest;
import com.omgservers.model.dto.client.DeleteClientMessagesResponse;
import com.omgservers.model.dto.client.DeleteClientRequest;
import com.omgservers.model.dto.client.DeleteClientResponse;
import com.omgservers.model.dto.client.DeleteClientRuntimeRefRequest;
import com.omgservers.model.dto.client.DeleteClientRuntimeRefResponse;
import com.omgservers.model.dto.client.FindClientRuntimeRefRequest;
import com.omgservers.model.dto.client.FindClientRuntimeRefResponse;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.GetClientRuntimeRefRequest;
import com.omgservers.model.dto.client.GetClientRuntimeRefResponse;
import com.omgservers.model.dto.client.InterchangeRequest;
import com.omgservers.model.dto.client.InterchangeResponse;
import com.omgservers.model.dto.client.SelectClientRuntimeRequest;
import com.omgservers.model.dto.client.SelectClientRuntimeResponse;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.dto.client.SyncClientRequest;
import com.omgservers.model.dto.client.SyncClientResponse;
import com.omgservers.model.dto.client.SyncClientRuntimeRefRequest;
import com.omgservers.model.dto.client.SyncClientRuntimeRefResponse;
import com.omgservers.model.dto.client.ViewClientMessagesRequest;
import com.omgservers.model.dto.client.ViewClientMessagesResponse;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsRequest;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsResponse;
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

    Uni<GetClientRuntimeRefResponse> getClientRuntimeRef(@Valid GetClientRuntimeRefRequest request);

    Uni<FindClientRuntimeRefResponse> findClientRuntimeRef(@Valid FindClientRuntimeRefRequest request);

    Uni<ViewClientRuntimeRefsResponse> viewClientRuntimeRefs(@Valid ViewClientRuntimeRefsRequest request);

    Uni<SyncClientRuntimeRefResponse> syncClientRuntimeRef(@Valid SyncClientRuntimeRefRequest request);

    Uni<DeleteClientRuntimeRefResponse> deleteClientRuntimeRef(@Valid DeleteClientRuntimeRefRequest request);
}
