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
import com.omgservers.schema.module.runtime.poolContainerRef.DeleteRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.DeleteRuntimePoolContainerRefResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.FindRuntimePoolContainerRefResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.GetRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.GetRuntimePoolContainerRefResponse;
import com.omgservers.schema.module.runtime.poolContainerRef.SyncRuntimePoolContainerRefRequest;
import com.omgservers.schema.module.runtime.poolContainerRef.SyncRuntimePoolContainerRefResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Runtime Module API")
@Path("/service/v1/module/runtime/request")
public interface RuntimeApi {

    @PUT
    @Path("/get-runtime")
    Uni<GetRuntimeResponse> execute(GetRuntimeRequest request);

    @PUT
    @Path("/sync-runtime")
    Uni<SyncRuntimeResponse> execute(SyncRuntimeRequest request);

    @PUT
    @Path("/delete-runtime")
    Uni<DeleteRuntimeResponse> execute(DeleteRuntimeRequest request);

    @PUT
    @Path("/sync-runtime-permission")
    Uni<SyncRuntimePermissionResponse> execute(SyncRuntimePermissionRequest request);

    @PUT
    @Path("/find-runtime-permission")
    Uni<FindRuntimePermissionResponse> execute(FindRuntimePermissionRequest request);

    @PUT
    @Path("/view-runtime-permissions")
    Uni<ViewRuntimePermissionsResponse> execute(ViewRuntimePermissionsRequest request);

    @PUT
    @Path("/delete-runtime-permission")
    Uni<DeleteRuntimePermissionResponse> execute(DeleteRuntimePermissionRequest request);

    @PUT
    @Path("/view-runtime-commands")
    Uni<ViewRuntimeCommandsResponse> execute(ViewRuntimeCommandsRequest request);

    @PUT
    @Path("/sync-runtime-command")
    Uni<SyncRuntimeCommandResponse> execute(SyncRuntimeCommandRequest request);

    @PUT
    @Path("/sync-client-command")
    Uni<SyncClientCommandResponse> execute(SyncClientCommandRequest request);

    @PUT
    @Path("/delete-runtime-command")
    Uni<DeleteRuntimeCommandResponse> execute(DeleteRuntimeCommandRequest request);

    @PUT
    @Path("/delete-runtime-commands")
    Uni<DeleteRuntimeCommandsResponse> execute(DeleteRuntimeCommandsRequest request);

    @PUT
    @Path("/get-runtime-assignment")
    Uni<GetRuntimeAssignmentResponse> execute(GetRuntimeAssignmentRequest request);

    @PUT
    @Path("/find-runtime-assignment")
    Uni<FindRuntimeAssignmentResponse> execute(FindRuntimeAssignmentRequest request);

    @PUT
    @Path("/view-runtime-assignment")
    Uni<ViewRuntimeAssignmentsResponse> execute(ViewRuntimeAssignmentsRequest request);

    @PUT
    @Path("/count-runtime-assignments")
    Uni<CountRuntimeAssignmentsResponse> execute(CountRuntimeAssignmentsRequest request);

    @PUT
    @Path("/sync-runtime-assignment")
    Uni<SyncRuntimeAssignmentResponse> execute(SyncRuntimeAssignmentRequest request);

    @PUT
    @Path("/update-runtime-assignment-last-activity")
    Uni<UpdateRuntimeAssignmentLastActivityResponse> execute(
            UpdateRuntimeAssignmentLastActivityRequest request);

    @PUT
    @Path("/delete-runtime-assignment")
    Uni<DeleteRuntimeAssignmentResponse> execute(DeleteRuntimeAssignmentRequest request);

    @PUT
    @Path("/get-runtime-pool-server-container-ref")
    Uni<GetRuntimePoolContainerRefResponse> execute(
            GetRuntimePoolContainerRefRequest request);

    @PUT
    @Path("/find-runtime-pool-server-container-ref")
    Uni<FindRuntimePoolContainerRefResponse> execute(
            FindRuntimePoolContainerRefRequest request);

    @PUT
    @Path("/sync-runtime-pool-server-container-ref")
    Uni<SyncRuntimePoolContainerRefResponse> execute(
            SyncRuntimePoolContainerRefRequest request);

    @PUT
    @Path("/delete-runtime-pool-server-container-ref")
    Uni<DeleteRuntimePoolContainerRefResponse> execute(
            DeleteRuntimePoolContainerRefRequest request);

    @PUT
    @Path("/interchange")
    Uni<InterchangeResponse> execute(InterchangeRequest request);
}
