package com.omgservers.service.shard.runtime.impl.service.runtimeService;

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
import jakarta.validation.Valid;

public interface RuntimeService {

    Uni<GetRuntimeResponse> execute(@Valid GetRuntimeRequest request);

    Uni<SyncRuntimeResponse> execute(@Valid SyncRuntimeRequest request);

    Uni<SyncRuntimeResponse> executeWithIdempotency(@Valid SyncRuntimeRequest request);

    Uni<DeleteRuntimeResponse> execute(@Valid DeleteRuntimeRequest request);

    Uni<SyncRuntimePermissionResponse> execute(@Valid SyncRuntimePermissionRequest request);

    Uni<ViewRuntimePermissionsResponse> execute(@Valid ViewRuntimePermissionsRequest request);

    Uni<FindRuntimePermissionResponse> execute(@Valid FindRuntimePermissionRequest request);

    Uni<DeleteRuntimePermissionResponse> execute(@Valid DeleteRuntimePermissionRequest request);

    Uni<ViewRuntimeCommandsResponse> execute(@Valid ViewRuntimeCommandsRequest request);

    Uni<SyncRuntimeCommandResponse> execute(@Valid SyncRuntimeCommandRequest request);

    Uni<SyncClientCommandResponse> execute(@Valid SyncClientCommandRequest request);

    Uni<SyncClientCommandResponse> executeWithIdempotency(@Valid SyncClientCommandRequest request);

    Uni<DeleteRuntimeCommandResponse> execute(@Valid DeleteRuntimeCommandRequest request);

    Uni<DeleteRuntimeCommandsResponse> execute(@Valid DeleteRuntimeCommandsRequest request);

    Uni<GetRuntimeAssignmentResponse> execute(@Valid GetRuntimeAssignmentRequest request);

    Uni<FindRuntimeAssignmentResponse> execute(@Valid FindRuntimeAssignmentRequest request);

    Uni<ViewRuntimeAssignmentsResponse> execute(@Valid ViewRuntimeAssignmentsRequest request);

    Uni<CountRuntimeAssignmentsResponse> execute(@Valid CountRuntimeAssignmentsRequest request);

    Uni<SyncRuntimeAssignmentResponse> execute(@Valid SyncRuntimeAssignmentRequest request);

    Uni<SyncRuntimeAssignmentResponse> executeWithIdempotency(@Valid SyncRuntimeAssignmentRequest request);

    Uni<UpdateRuntimeAssignmentLastActivityResponse> execute(@Valid UpdateRuntimeAssignmentLastActivityRequest request);

    Uni<DeleteRuntimeAssignmentResponse> execute(@Valid DeleteRuntimeAssignmentRequest request);

    Uni<GetRuntimePoolContainerRefResponse> execute(@Valid GetRuntimePoolContainerRefRequest request);

    Uni<FindRuntimePoolContainerRefResponse> execute(@Valid FindRuntimePoolContainerRefRequest request);

    Uni<SyncRuntimePoolContainerRefResponse> execute(@Valid SyncRuntimePoolContainerRefRequest request);

    Uni<DeleteRuntimePoolContainerRefResponse> execute(@Valid DeleteRuntimePoolContainerRefRequest request);

    Uni<InterchangeResponse> execute(@Valid InterchangeRequest request);
}
