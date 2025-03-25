package com.omgservers.service.shard.client.impl.service.webService.impl.api;

import com.omgservers.schema.module.client.client.*;
import com.omgservers.schema.module.client.clientMessage.*;
import com.omgservers.schema.module.client.clientRuntimeRef.*;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Client Shard API")
@Path("/service/v1/shard/client/request")
public interface ClientApi {

    /*
    Client
     */

    @POST
    @Path("/get-client")
    Uni<GetClientResponse> execute(GetClientRequest request);

    @POST
    @Path("/sync-client")
    Uni<SyncClientResponse> execute(SyncClientRequest request);

    @POST
    @Path("/delete-client")
    Uni<DeleteClientResponse> execute(DeleteClientRequest request);

    /*
    ClientMessage
     */

    @POST
    @Path("/view-client-messages")
    Uni<ViewClientMessagesResponse> execute(ViewClientMessagesRequest request);

    @POST
    @Path("/sync-client-message")
    Uni<SyncClientMessageResponse> execute(SyncClientMessageRequest request);

    @POST
    @Path("/delete-client-messages")
    Uni<DeleteClientMessagesResponse> execute(DeleteClientMessagesRequest request);

    @POST
    @Path("/interchange-messages")
    Uni<InterchangeMessagesResponse> execute(InterchangeMessagesRequest request);

    /*
    ClientRuntimeRef
     */

    @POST
    @Path("/get-client-runtime-ref")
    Uni<GetClientRuntimeRefResponse> execute(GetClientRuntimeRefRequest request);

    @POST
    @Path("/find-client-runtime-ref")
    Uni<FindClientRuntimeRefResponse> execute(FindClientRuntimeRefRequest request);

    @POST
    @Path("/view-client-runtime-refs")
    Uni<ViewClientRuntimeRefsResponse> execute(ViewClientRuntimeRefsRequest request);

    @POST
    @Path("/sync-client-runtime-ref")
    Uni<SyncClientRuntimeRefResponse> execute(SyncClientRuntimeRefRequest request);

    @POST
    @Path("/delete-client-runtime-ref")
    Uni<DeleteClientRuntimeRefResponse> execute(DeleteClientRuntimeRefRequest request);
}
