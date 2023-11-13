package com.omgservers.service.module.runtime.impl.service.webService.impl.api;

import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.runtime.DoBroadcastMessageRequest;
import com.omgservers.model.dto.runtime.DoBroadcastMessageResponse;
import com.omgservers.model.dto.runtime.DoChangePlayerRequest;
import com.omgservers.model.dto.runtime.DoChangePlayerResponse;
import com.omgservers.model.dto.runtime.DoKickClientRequest;
import com.omgservers.model.dto.runtime.DoKickClientResponse;
import com.omgservers.model.dto.runtime.DoMulticastMessageRequest;
import com.omgservers.model.dto.runtime.DoMulticastMessageResponse;
import com.omgservers.model.dto.runtime.DoRespondClientRequest;
import com.omgservers.model.dto.runtime.DoRespondClientResponse;
import com.omgservers.model.dto.runtime.DoSetAttributesRequest;
import com.omgservers.model.dto.runtime.DoSetAttributesResponse;
import com.omgservers.model.dto.runtime.DoSetProfileRequest;
import com.omgservers.model.dto.runtime.DoSetProfileResponse;
import com.omgservers.model.dto.runtime.DoStopRuntimeRequest;
import com.omgservers.model.dto.runtime.DoStopRuntimeResponse;
import com.omgservers.model.dto.runtime.DoUnicastMessageRequest;
import com.omgservers.model.dto.runtime.DoUnicastMessageResponse;
import com.omgservers.model.dto.runtime.FindRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.FindRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeGrantResponse;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeGrantsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeGrantsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/runtime-api/v1/request")
public interface RuntimeApi {

    @PUT
    @Path("/get-runtime")
    Uni<GetRuntimeResponse> getRuntime(GetRuntimeRequest request);

    @PUT
    @Path("/sync-runtime")
    Uni<SyncRuntimeResponse> syncRuntime(SyncRuntimeRequest request);

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
    @Path("/delete-runtime-command")
    Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(DeleteRuntimeCommandRequest request);

    @PUT
    @Path("/delete-runtime-commands")
    Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(DeleteRuntimeCommandsRequest request);

    @PUT
    @Path("/sync-runtime-grant")
    Uni<SyncRuntimeGrantResponse> syncRuntimeGrant(SyncRuntimeGrantRequest request);

    @PUT
    @Path("/view-runtime-grants")
    Uni<ViewRuntimeGrantsResponse> viewRuntimeGrants(ViewRuntimeGrantsRequest request);

    @PUT
    @Path("/find-runtime-grant")
    Uni<FindRuntimeGrantResponse> findRuntimeGrant(FindRuntimeGrantRequest request);

    @PUT
    @Path("/delete-runtime-grant")
    Uni<DeleteRuntimeGrantResponse> deleteRuntimeGrant(DeleteRuntimeGrantRequest request);

    @PUT
    @Path("/do-respond-client")
    Uni<DoRespondClientResponse> doRespondClient(DoRespondClientRequest request);

    @PUT
    @Path("/do-set-attributes")
    Uni<DoSetAttributesResponse> doSetAttributes(DoSetAttributesRequest request);

    @PUT
    @Path("/do-set-profile")
    Uni<DoSetProfileResponse> doSetProfile(DoSetProfileRequest request);

    @PUT
    @Path("/do-kick-client")
    Uni<DoKickClientResponse> doKickClient(DoKickClientRequest request);

    @PUT
    @Path("/do-stop-runtime")
    Uni<DoStopRuntimeResponse> doStopRuntime(DoStopRuntimeRequest request);

    @PUT
    @Path("/do-change-player")
    Uni<DoChangePlayerResponse> doChangePlayer(DoChangePlayerRequest request);

    @PUT
    @Path("/do-unicast-message")
    Uni<DoUnicastMessageResponse> doUnicastMessage(DoUnicastMessageRequest request);

    @PUT
    @Path("/do-multicast-message")
    Uni<DoMulticastMessageResponse> doMulticastMessage(DoMulticastMessageRequest request);

    @PUT
    @Path("/do-broadcast-message")
    Uni<DoBroadcastMessageResponse> doBroadcastMessage(DoBroadcastMessageRequest request);
}
