package com.omgservers.service.module.runtime.impl.service.webService;

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

public interface WebService {
    Uni<GetRuntimeResponse> getRuntime(GetRuntimeRequest request);

    Uni<SyncRuntimeResponse> syncLobbyRuntime(SyncRuntimeRequest request);

    Uni<DeleteRuntimeResponse> deleteRuntime(DeleteRuntimeRequest request);

    Uni<SyncRuntimePermissionResponse> syncRuntimePermission(SyncRuntimePermissionRequest request);

    Uni<ViewRuntimePermissionsResponse> viewRuntimePermissions(ViewRuntimePermissionsRequest request);

    Uni<FindRuntimePermissionResponse> findRuntimePermission(FindRuntimePermissionRequest request);

    Uni<DeleteRuntimePermissionResponse> deleteRuntimePermission(DeleteRuntimePermissionRequest request);

    Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(ViewRuntimeCommandsRequest request);

    Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(SyncRuntimeCommandRequest request);

    Uni<SyncClientCommandResponse> syncClientCommand(SyncClientCommandRequest request);

    Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(DeleteRuntimeCommandRequest request);

    Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(DeleteRuntimeCommandsRequest request);

    Uni<GetRuntimeAssignmentResponse> getRuntimeAssignment(GetRuntimeAssignmentRequest request);

    Uni<FindRuntimeAssignmentResponse> findRuntimeAssignment(FindRuntimeAssignmentRequest request);

    Uni<ViewRuntimeAssignmentsResponse> viewRuntimeAssignments(ViewRuntimeAssignmentsRequest request);

    Uni<CountRuntimeAssignmentsResponse> countRuntimeAssignments(CountRuntimeAssignmentsRequest request);

    Uni<SyncRuntimeAssignmentResponse> syncRuntimeAssignment(SyncRuntimeAssignmentRequest request);

    Uni<DeleteRuntimeAssignmentResponse> deleteRuntimeAssignment(DeleteRuntimeAssignmentRequest request);

    Uni<GetRuntimePoolServerContainerRefResponse> getRuntimePoolServerContainerRef(
            GetRuntimePoolServerContainerRefRequest request);

    Uni<FindRuntimePoolServerContainerRefResponse> findRuntimePoolServerContainerRef(
            FindRuntimePoolServerContainerRefRequest request);

    Uni<SyncRuntimePoolServerContainerRefResponse> syncRuntimePoolServerContainerRef(
            SyncRuntimePoolServerContainerRefRequest request);

    Uni<DeleteRuntimePoolServerContainerRefResponse> deleteRuntimePoolServerContainerRef(
            DeleteRuntimePoolServerContainerRefRequest request);

    Uni<InterchangeResponse> interchange(InterchangeRequest request);
}
