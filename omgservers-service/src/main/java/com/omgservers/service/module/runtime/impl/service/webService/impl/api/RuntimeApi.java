package com.omgservers.service.module.runtime.impl.service.webService.impl.api;

import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.CountRuntimeAssignmentsResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.schema.module.runtime.DeleteRuntimeRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeResponse;
import com.omgservers.schema.module.runtime.FindRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.FindRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.FindRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.FindRuntimePermissionResponse;
import com.omgservers.schema.module.runtime.GetRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.GetRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.GetRuntimeRequest;
import com.omgservers.schema.module.runtime.GetRuntimeResponse;
import com.omgservers.schema.module.runtime.InterchangeRequest;
import com.omgservers.schema.module.runtime.InterchangeResponse;
import com.omgservers.schema.module.runtime.SyncClientCommandRequest;
import com.omgservers.schema.module.runtime.SyncClientCommandResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeAssignmentResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeCommandRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeCommandResponse;
import com.omgservers.schema.module.runtime.SyncRuntimePermissionRequest;
import com.omgservers.schema.module.runtime.SyncRuntimePermissionResponse;
import com.omgservers.schema.module.runtime.SyncRuntimeRequest;
import com.omgservers.schema.module.runtime.SyncRuntimeResponse;
import com.omgservers.schema.module.runtime.UpdateRuntimeAssignmentLastActivityRequest;
import com.omgservers.schema.module.runtime.UpdateRuntimeAssignmentLastActivityResponse;
import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimeAssignmentsResponse;
import com.omgservers.schema.module.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.schema.module.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.schema.module.runtime.ViewRuntimePermissionsResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefResponse;
import com.omgservers.schema.module.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefRequest;
import com.omgservers.schema.module.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Runtime Module API")
@Path("/omgservers/v1/module/runtime/request")
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
    @Path("/get-runtime-assignment")
    Uni<GetRuntimeAssignmentResponse> getRuntimeAssignment(GetRuntimeAssignmentRequest request);

    @PUT
    @Path("/find-runtime-assignment")
    Uni<FindRuntimeAssignmentResponse> findRuntimeAssignment(FindRuntimeAssignmentRequest request);

    @PUT
    @Path("/view-runtime-assignment")
    Uni<ViewRuntimeAssignmentsResponse> viewRuntimeAssignments(ViewRuntimeAssignmentsRequest request);

    @PUT
    @Path("/count-runtime-assignments")
    Uni<CountRuntimeAssignmentsResponse> countRuntimeAssignments(CountRuntimeAssignmentsRequest request);

    @PUT
    @Path("/sync-runtime-assignment")
    Uni<SyncRuntimeAssignmentResponse> syncRuntimeAssignment(SyncRuntimeAssignmentRequest request);

    @PUT
    @Path("/update-runtime-assignment-last-activity")
    Uni<UpdateRuntimeAssignmentLastActivityResponse> updateRuntimeAssignmentLastActivity(
            UpdateRuntimeAssignmentLastActivityRequest request);

    @PUT
    @Path("/delete-runtime-assignment")
    Uni<DeleteRuntimeAssignmentResponse> deleteRuntimeAssignment(DeleteRuntimeAssignmentRequest request);

    @PUT
    @Path("/get-runtime-pool-server-container-ref")
    Uni<GetRuntimePoolServerContainerRefResponse> getRuntimePoolServerContainerRef(
            GetRuntimePoolServerContainerRefRequest request);

    @PUT
    @Path("/find-runtime-pool-server-container-ref")
    Uni<FindRuntimePoolServerContainerRefResponse> findRuntimePoolServerContainerRef(
            FindRuntimePoolServerContainerRefRequest request);

    @PUT
    @Path("/sync-runtime-pool-server-container-ref")
    Uni<SyncRuntimePoolServerContainerRefResponse> syncRuntimePoolServerContainerRef(
            SyncRuntimePoolServerContainerRefRequest request);

    @PUT
    @Path("/delete-runtime-pool-server-container-ref")
    Uni<DeleteRuntimePoolServerContainerRefResponse> deleteRuntimePoolServerContainerRef(
            DeleteRuntimePoolServerContainerRefRequest request);

    @PUT
    @Path("/interchange")
    Uni<InterchangeResponse> interchange(InterchangeRequest request);
}
