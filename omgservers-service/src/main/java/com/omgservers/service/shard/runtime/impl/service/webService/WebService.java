package com.omgservers.service.shard.runtime.impl.service.webService;

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

public interface WebService {
    Uni<GetRuntimeResponse> execute(GetRuntimeRequest request);

    Uni<SyncRuntimeResponse> execute(SyncRuntimeRequest request);

    Uni<DeleteRuntimeResponse> execute(DeleteRuntimeRequest request);

    Uni<SyncRuntimePermissionResponse> execute(SyncRuntimePermissionRequest request);

    Uni<ViewRuntimePermissionsResponse> execute(ViewRuntimePermissionsRequest request);

    Uni<FindRuntimePermissionResponse> execute(FindRuntimePermissionRequest request);

    Uni<DeleteRuntimePermissionResponse> execute(DeleteRuntimePermissionRequest request);

    Uni<ViewRuntimeCommandsResponse> execute(ViewRuntimeCommandsRequest request);

    Uni<SyncRuntimeCommandResponse> execute(SyncRuntimeCommandRequest request);

    Uni<SyncClientCommandResponse> execute(SyncClientCommandRequest request);

    Uni<DeleteRuntimeCommandResponse> execute(DeleteRuntimeCommandRequest request);

    Uni<DeleteRuntimeCommandsResponse> execute(DeleteRuntimeCommandsRequest request);

    Uni<GetRuntimeAssignmentResponse> execute(GetRuntimeAssignmentRequest request);

    Uni<FindRuntimeAssignmentResponse> execute(FindRuntimeAssignmentRequest request);

    Uni<ViewRuntimeAssignmentsResponse> execute(ViewRuntimeAssignmentsRequest request);

    Uni<CountRuntimeAssignmentsResponse> execute(CountRuntimeAssignmentsRequest request);

    Uni<SyncRuntimeAssignmentResponse> execute(SyncRuntimeAssignmentRequest request);

    Uni<UpdateRuntimeAssignmentLastActivityResponse> execute(UpdateRuntimeAssignmentLastActivityRequest request);

    Uni<DeleteRuntimeAssignmentResponse> execute(DeleteRuntimeAssignmentRequest request);

    Uni<GetRuntimePoolContainerRefResponse> execute(GetRuntimePoolContainerRefRequest request);

    Uni<FindRuntimePoolContainerRefResponse> execute(FindRuntimePoolContainerRefRequest request);

    Uni<SyncRuntimePoolContainerRefResponse> execute(SyncRuntimePoolContainerRefRequest request);

    Uni<DeleteRuntimePoolContainerRefResponse> execute(DeleteRuntimePoolContainerRefRequest request);

    Uni<InterchangeResponse> execute(InterchangeRequest request);
}
