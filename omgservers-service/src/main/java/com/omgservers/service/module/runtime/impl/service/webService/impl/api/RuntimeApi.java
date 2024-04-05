package com.omgservers.service.module.runtime.impl.service.webService.impl.api;

import com.omgservers.model.dto.runtime.CountRuntimeAssignmentsRequest;
import com.omgservers.model.dto.runtime.CountRuntimeAssignmentsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.dto.runtime.FindRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.FindRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.FindRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.FindRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.GetRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.GetRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.model.dto.runtime.InterchangeRequest;
import com.omgservers.model.dto.runtime.InterchangeResponse;
import com.omgservers.model.dto.runtime.SyncClientCommandRequest;
import com.omgservers.model.dto.runtime.SyncClientCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeAssignmentRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeAssignmentResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeCommandResponse;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionRequest;
import com.omgservers.model.dto.runtime.SyncRuntimePermissionResponse;
import com.omgservers.model.dto.runtime.SyncRuntimeRequest;
import com.omgservers.model.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeAssignmentsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeAssignmentsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimePermissionsResponse;
import com.omgservers.model.dto.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.DeleteRuntimePoolServerContainerRefResponse;
import com.omgservers.model.dto.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.FindRuntimePoolServerContainerRefResponse;
import com.omgservers.model.dto.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.GetRuntimePoolServerContainerRefResponse;
import com.omgservers.model.dto.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefRequest;
import com.omgservers.model.dto.runtime.poolServerContainerRef.SyncRuntimePoolServerContainerRefResponse;
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
