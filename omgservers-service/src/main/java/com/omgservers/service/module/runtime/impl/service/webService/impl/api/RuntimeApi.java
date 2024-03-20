package com.omgservers.service.module.runtime.impl.service.webService.impl.api;

import com.omgservers.model.dto.runtime.CountRuntimeClientsRequest;
import com.omgservers.model.dto.runtime.CountRuntimeClientsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeClientRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeClientResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.runtime.FindRuntimeClientRequest;
import com.omgservers.model.dto.runtime.FindRuntimeClientResponse;
import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeClientRequest;
import com.omgservers.model.dto.runtime.GetRuntimeClientResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.InterchangeRequest;
import com.omgservers.model.dto.runtime.InterchangeResponse;
import com.omgservers.model.dto.runtime.SyncClientCommandRequest;
import com.omgservers.model.dto.runtime.SyncClientCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeClientRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeClientResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeClientsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeClientsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsResponse;
import com.omgservers.service.configuration.ServiceOpenApiConfiguration;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Runtime API")
@Path("/omgservers/runtime-api/v1/request")
@SecurityRequirement(name = ServiceOpenApiConfiguration.SERVICE_SECURITY_SCHEMA)
public interface RuntimeApi {

    @PUT
    @Path("/get-runtime")
    Uni<GetRuntimeResponse> getRuntime(GetRuntimeRequest request);

    @PUT
    @Path("/sync-runtime")
    Uni<SyncRuntimeResponse> syncLobbyRuntime(SyncRuntimeRequest request);

    @PUT
    @Path("/delete-runtime")
    Uni<DeleteRuntimeResponse> deleteRuntime(DeleteRuntimeRequest request);

    @PUT
    @Path("/sync-runtime-permission")
    Uni<SyncRuntimePermissionResponse> syncRuntimePermission(SyncRuntimePermissionRequest request);

    @PUT
    @Path("/find-runtime-permission")
    Uni<FindRuntimePermissionResponse> findRuntimePermission(FindRuntimePermissionRequest request);

    @PUT
    @Path("/view-runtime-permissions")
    Uni<ViewRuntimePermissionsResponse> viewRuntimePermissions(ViewRuntimePermissionsRequest request);

    @PUT
    @Path("/delete-runtime-permission")
    Uni<DeleteRuntimePermissionResponse> deleteRuntimePermission(DeleteRuntimePermissionRequest request);

    @PUT
    @Path("/view-runtime-commands")
    Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(ViewRuntimeCommandsRequest request);

    @PUT
    @Path("/sync-runtime-command")
    Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(SyncRuntimeCommandRequest request);

    @PUT
    @Path("/sync-client-command")
    Uni<SyncClientCommandResponse> syncClientCommand(SyncClientCommandRequest request);

    @PUT
    @Path("/delete-runtime-command")
    Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(DeleteRuntimeCommandRequest request);

    @PUT
    @Path("/delete-runtime-commands")
    Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(DeleteRuntimeCommandsRequest request);

    @PUT
    @Path("/get-runtime-client")
    Uni<GetRuntimeClientResponse> getRuntimeClient(GetRuntimeClientRequest request);

    @PUT
    @Path("/find-runtime-client")
    Uni<FindRuntimeClientResponse> findRuntimeClient(FindRuntimeClientRequest request);

    @PUT
    @Path("/view-runtime-clients")
    Uni<ViewRuntimeClientsResponse> viewRuntimeClients(ViewRuntimeClientsRequest request);

    @PUT
    @Path("/count-runtime-clients")
    Uni<CountRuntimeClientsResponse> countRuntimeClients(CountRuntimeClientsRequest request);

    @PUT
    @Path("/sync-runtime-client")
    Uni<SyncRuntimeClientResponse> syncRuntimeClient(SyncRuntimeClientRequest request);

    @PUT
    @Path("/delete-runtime-client")
    Uni<DeleteRuntimeClientResponse> deleteRuntimeClient(DeleteRuntimeClientRequest request);

    @PUT
    @Path("/interchange")
    Uni<InterchangeResponse> interchange(InterchangeRequest request);
}
