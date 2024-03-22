package com.omgservers.service.module.client.impl.service.webService.impl.api;

import com.omgservers.model.dto.client.DeleteClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.DeleteClientMatchmakerRefResponse;
import com.omgservers.model.dto.client.DeleteClientMessagesRequest;
import com.omgservers.model.dto.client.DeleteClientMessagesResponse;
import com.omgservers.model.dto.client.DeleteClientRequest;
import com.omgservers.model.dto.client.DeleteClientResponse;
import com.omgservers.model.dto.client.DeleteClientRuntimeRefRequest;
import com.omgservers.model.dto.client.DeleteClientRuntimeRefResponse;
import com.omgservers.model.dto.client.FindClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.FindClientMatchmakerRefResponse;
import com.omgservers.model.dto.client.FindClientRuntimeRefRequest;
import com.omgservers.model.dto.client.FindClientRuntimeRefResponse;
import com.omgservers.model.dto.client.GetClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.GetClientMatchmakerRefResponse;
import com.omgservers.model.dto.client.GetClientRequest;
import com.omgservers.model.dto.client.GetClientResponse;
import com.omgservers.model.dto.client.GetClientRuntimeRefRequest;
import com.omgservers.model.dto.client.GetClientRuntimeRefResponse;
import com.omgservers.model.dto.client.InterchangeRequest;
import com.omgservers.model.dto.client.InterchangeResponse;
import com.omgservers.model.dto.client.SyncClientMatchmakerRefRequest;
import com.omgservers.model.dto.client.SyncClientMatchmakerRefResponse;
import com.omgservers.model.dto.client.SyncClientMessageRequest;
import com.omgservers.model.dto.client.SyncClientMessageResponse;
import com.omgservers.model.dto.client.SyncClientRequest;
import com.omgservers.model.dto.client.SyncClientResponse;
import com.omgservers.model.dto.client.SyncClientRuntimeRefRequest;
import com.omgservers.model.dto.client.SyncClientRuntimeRefResponse;
import com.omgservers.model.dto.client.ViewClientMatchmakerRefsRequest;
import com.omgservers.model.dto.client.ViewClientMatchmakerRefsResponse;
import com.omgservers.model.dto.client.ViewClientMessagesRequest;
import com.omgservers.model.dto.client.ViewClientMessagesResponse;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsRequest;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Client Module API")
@Path("/omgservers/v1/module/client/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.SERVICE_SECURITY_SCHEMA)
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
    @Path("/interchange")
    Uni<InterchangeResponse> interchange(InterchangeRequest request);

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
    @Path("/get-client-runtime-ref")
    Uni<GetClientRuntimeRefResponse> getClientRuntimeRef(GetClientRuntimeRefRequest request);

    @PUT
    @Path("/find-client-runtime-ref")
    Uni<FindClientRuntimeRefResponse> findClientRuntimeRef(FindClientRuntimeRefRequest request);

    @PUT
    @Path("/view-client-runtime-refs")
    Uni<ViewClientRuntimeRefsResponse> viewClientRuntimeRefs(ViewClientRuntimeRefsRequest request);

    @PUT
    @Path("/sync-client-runtime-ref")
    Uni<SyncClientRuntimeRefResponse> syncClientRuntimeRef(SyncClientRuntimeRefRequest request);

    @PUT
    @Path("/delete-client-runtime-ref")
    Uni<DeleteClientRuntimeRefResponse> deleteClientRuntimeRef(DeleteClientRuntimeRefRequest request);

    @PUT
    @Path("/get-client-matchmaker-ref")
    Uni<GetClientMatchmakerRefResponse> getClientMatchmakerRef(GetClientMatchmakerRefRequest request);

    @PUT
    @Path("/find-client-matchmaker-ref")
    Uni<FindClientMatchmakerRefResponse> findClientMatchmakerRef(FindClientMatchmakerRefRequest request);

    @PUT
    @Path("/view-client-matchmaker-refs")
    Uni<ViewClientMatchmakerRefsResponse> viewClientMatchmakerRefs(ViewClientMatchmakerRefsRequest request);

    @PUT
    @Path("/sync-client-matchmaker-ref")
    Uni<SyncClientMatchmakerRefResponse> syncClientMatchmakerRef(SyncClientMatchmakerRefRequest request);

    @PUT
    @Path("/delete-client-matchmaker-ref")
    Uni<DeleteClientMatchmakerRefResponse> deleteClientMatchmakerRef(DeleteClientMatchmakerRefRequest request);
}
