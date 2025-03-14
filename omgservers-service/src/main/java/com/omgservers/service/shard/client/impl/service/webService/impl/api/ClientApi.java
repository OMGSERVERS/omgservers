package com.omgservers.service.shard.client.impl.service.webService.impl.api;

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
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Client Shard API")
@Path("/service/v1/shard/client/request")
public interface ClientApi {

    @POST
    @Path("/get-client")
    Uni<GetClientResponse> getClient(GetClientRequest request);

    @POST
    @Path("/sync-client")
    Uni<SyncClientResponse> syncClient(SyncClientRequest request);

    @POST
    @Path("/delete-client")
    Uni<DeleteClientResponse> deleteClient(DeleteClientRequest request);

    @POST
    @Path("/interchange")
    Uni<InterchangeResponse> interchange(InterchangeRequest request);

    @POST
    @Path("/view-client-messages")
    Uni<ViewClientMessagesResponse> viewClientMessages(ViewClientMessagesRequest request);

    @POST
    @Path("/sync-client-message")
    Uni<SyncClientMessageResponse> syncClientMessage(SyncClientMessageRequest request);

    @POST
    @Path("/delete-client-messages")
    Uni<DeleteClientMessagesResponse> deleteClientMessages(DeleteClientMessagesRequest request);

    @POST
    @Path("/get-client-runtime-ref")
    Uni<GetClientRuntimeRefResponse> getClientRuntimeRef(GetClientRuntimeRefRequest request);

    @POST
    @Path("/find-client-runtime-ref")
    Uni<FindClientRuntimeRefResponse> findClientRuntimeRef(FindClientRuntimeRefRequest request);

    @POST
    @Path("/view-client-runtime-refs")
    Uni<ViewClientRuntimeRefsResponse> viewClientRuntimeRefs(ViewClientRuntimeRefsRequest request);

    @POST
    @Path("/sync-client-runtime-ref")
    Uni<SyncClientRuntimeRefResponse> syncClientRuntimeRef(SyncClientRuntimeRefRequest request);

    @POST
    @Path("/delete-client-runtime-ref")
    Uni<DeleteClientRuntimeRefResponse> deleteClientRuntimeRef(DeleteClientRuntimeRefRequest request);

    @POST
    @Path("/get-client-matchmaker-ref")
    Uni<GetClientMatchmakerRefResponse> getClientMatchmakerRef(GetClientMatchmakerRefRequest request);

    @POST
    @Path("/find-client-matchmaker-ref")
    Uni<FindClientMatchmakerRefResponse> findClientMatchmakerRef(FindClientMatchmakerRefRequest request);

    @POST
    @Path("/view-client-matchmaker-refs")
    Uni<ViewClientMatchmakerRefsResponse> viewClientMatchmakerRefs(ViewClientMatchmakerRefsRequest request);

    @POST
    @Path("/sync-client-matchmaker-ref")
    Uni<SyncClientMatchmakerRefResponse> syncClientMatchmakerRef(SyncClientMatchmakerRefRequest request);

    @POST
    @Path("/delete-client-matchmaker-ref")
    Uni<DeleteClientMatchmakerRefResponse> deleteClientMatchmakerRef(DeleteClientMatchmakerRefRequest request);
}
