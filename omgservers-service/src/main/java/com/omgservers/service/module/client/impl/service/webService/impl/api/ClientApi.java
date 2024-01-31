package com.omgservers.service.module.client.impl.service.webService.impl.api;

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
import com.omgservers.model.dto.client.HandleMessageRequest;
import com.omgservers.model.dto.client.HandleMessageResponse;
import com.omgservers.model.dto.client.ReceiveMessagesRequest;
import com.omgservers.model.dto.client.ReceiveMessagesResponse;
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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/client-api/v1/request")
public interface ClientApi {

    @PUT
    @Path("/get-client")
    Uni<GetClientResponse> getClient(GetClientRequest request);

    @PUT
    @Path("/sync-client")
    Uni<SyncClientResponse> syncClient(SyncClientRequest request);

    @PUT
    @Path("/delete-client")
    Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request);

    @PUT
    @Path("/handle-message")
    Uni<HandleMessageResponse> handleMessage(HandleMessageRequest request);

    @PUT
    @Path("/receive-messages")
    Uni<ReceiveMessagesResponse> receiveMessages(ReceiveMessagesRequest request);

    @PUT
    @Path("/view-client-messages")
    Uni<ViewClientMessagesResponse> viewClientMessages(ViewClientMessagesRequest request);

    @PUT
    @Path("/sync-client-message")
    Uni<SyncClientMessageResponse> syncClientMessage(SyncClientMessageRequest request);

    @PUT
    @Path("/delete-client-messages")
    Uni<DeleteClientMessagesResponse> deleteClientMessages(DeleteClientMessagesRequest request);

    @PUT
    @Path("/get-client-runtime")
    Uni<GetClientRuntimeResponse> getClientRuntime(GetClientRuntimeRequest request);

    @PUT
    @Path("/find-client-runtime")
    Uni<FindClientRuntimeResponse> findClientRuntime(FindClientRuntimeRequest request);

    @PUT
    @Path("/view-client-runtimes")
    Uni<ViewClientRuntimesResponse> viewClientRuntimes(ViewClientRuntimesRequest request);

    @PUT
    @Path("/select-client-runtime")
    Uni<SelectClientRuntimeResponse> selectClientRuntime(SelectClientRuntimeRequest request);

    @PUT
    @Path("/sync-client-runtime")
    Uni<SyncClientRuntimeResponse> syncClientRuntime(SyncClientRuntimeRequest request);

    @PUT
    @Path("/delete-client-runtime")
    Uni<DeleteClientRuntimeResponse> deleteClientRuntime(DeleteClientRuntimeRequest request);
}
