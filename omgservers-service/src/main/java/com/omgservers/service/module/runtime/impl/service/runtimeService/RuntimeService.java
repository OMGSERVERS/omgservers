package com.omgservers.service.module.runtime.impl.service.runtimeService;

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
import com.omgservers.model.dto.runtime.serverRuntimeRef.DeleteRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.DeleteRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.runtime.serverRuntimeRef.FindRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.FindRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.runtime.serverRuntimeRef.GetRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.GetRuntimeServerContainerRefResponse;
import com.omgservers.model.dto.runtime.serverRuntimeRef.SyncRuntimeServerContainerRefRequest;
import com.omgservers.model.dto.runtime.serverRuntimeRef.SyncRuntimeServerContainerRefResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface RuntimeService {

    Uni<GetRuntimeResponse> getRuntime(@Valid GetRuntimeRequest request);

    Uni<SyncRuntimeResponse> syncRuntime(@Valid SyncRuntimeRequest request);

    Uni<DeleteRuntimeResponse> deleteRuntime(@Valid DeleteRuntimeRequest request);

    Uni<SyncRuntimePermissionResponse> syncRuntimePermission(@Valid SyncRuntimePermissionRequest request);

    Uni<ViewRuntimePermissionsResponse> viewRuntimePermissions(@Valid ViewRuntimePermissionsRequest request);

    Uni<FindRuntimePermissionResponse> findRuntimePermission(@Valid FindRuntimePermissionRequest request);

    Uni<DeleteRuntimePermissionResponse> deleteRuntimePermission(@Valid DeleteRuntimePermissionRequest request);

    Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(@Valid ViewRuntimeCommandsRequest request);

    Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(@Valid SyncRuntimeCommandRequest request);

    Uni<SyncClientCommandResponse> syncClientCommand(@Valid SyncClientCommandRequest request);

    Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(@Valid DeleteRuntimeCommandRequest request);

    Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(@Valid DeleteRuntimeCommandsRequest request);

    Uni<GetRuntimeAssignmentResponse> getRuntimeAssignment(@Valid GetRuntimeAssignmentRequest request);

    Uni<FindRuntimeAssignmentResponse> findRuntimeAssignment(@Valid FindRuntimeAssignmentRequest request);

    Uni<ViewRuntimeAssignmentsResponse> viewRuntimeAssignments(@Valid ViewRuntimeAssignmentsRequest request);

    Uni<CountRuntimeAssignmentsResponse> countRuntimeAssignments(@Valid CountRuntimeAssignmentsRequest request);

    Uni<SyncRuntimeAssignmentResponse> syncRuntimeAssignment(@Valid SyncRuntimeAssignmentRequest request);

    Uni<DeleteRuntimeAssignmentResponse> deleteRuntimeAssignment(@Valid DeleteRuntimeAssignmentRequest request);

    Uni<GetRuntimeServerContainerRefResponse> getRuntimeServerContainerRef(
            @Valid GetRuntimeServerContainerRefRequest request);

    Uni<FindRuntimeServerContainerRefResponse> findRuntimeServerContainerRef(
            @Valid FindRuntimeServerContainerRefRequest request);

    Uni<SyncRuntimeServerContainerRefResponse> syncRuntimeServerContainerRef(
            @Valid SyncRuntimeServerContainerRefRequest request);

    Uni<DeleteRuntimeServerContainerRefResponse> deleteRuntimeServerContainerRef(
            @Valid DeleteRuntimeServerContainerRefRequest request);

    Uni<InterchangeResponse> interchange(@Valid InterchangeRequest request);
}
