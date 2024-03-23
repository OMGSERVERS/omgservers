package com.omgservers.service.module.runtime.impl.service.webService.impl;

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
import com.omgservers.service.module.runtime.impl.service.runtimeService.RuntimeService;
import com.omgservers.service.module.runtime.impl.service.webService.WebService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class WebServiceImpl implements WebService {

    final RuntimeService runtimeService;

    @Override
    public Uni<SyncRuntimeResponse> syncLobbyRuntime(final SyncRuntimeRequest request) {
        return runtimeService.syncRuntime(request);
    }

    @Override
    public Uni<GetRuntimeResponse> getRuntime(final GetRuntimeRequest request) {
        return runtimeService.getRuntime(request);
    }

    @Override
    public Uni<DeleteRuntimeResponse> deleteRuntime(final DeleteRuntimeRequest request) {
        return runtimeService.deleteRuntime(request);
    }

    @Override
    public Uni<SyncRuntimePermissionResponse> syncRuntimePermission(final SyncRuntimePermissionRequest request) {
        return runtimeService.syncRuntimePermission(request);
    }

    @Override
    public Uni<ViewRuntimePermissionsResponse> viewRuntimePermissions(final ViewRuntimePermissionsRequest request) {
        return runtimeService.viewRuntimePermissions(request);
    }

    @Override
    public Uni<FindRuntimePermissionResponse> findRuntimePermission(final FindRuntimePermissionRequest request) {
        return runtimeService.findRuntimePermission(request);
    }

    @Override
    public Uni<DeleteRuntimePermissionResponse> deleteRuntimePermission(final DeleteRuntimePermissionRequest request) {
        return runtimeService.deleteRuntimePermission(request);
    }

    @Override
    public Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(final SyncRuntimeCommandRequest request) {
        return runtimeService.syncRuntimeCommand(request);
    }

    @Override
    public Uni<SyncClientCommandResponse> syncClientCommand(final SyncClientCommandRequest request) {
        return runtimeService.syncClientCommand(request);
    }

    @Override
    public Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(final DeleteRuntimeCommandRequest request) {
        return runtimeService.deleteRuntimeCommand(request);
    }

    @Override
    public Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(final ViewRuntimeCommandsRequest request) {
        return runtimeService.viewRuntimeCommands(request);
    }

    @Override
    public Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(final DeleteRuntimeCommandsRequest request) {
        return runtimeService.deleteRuntimeCommands(request);
    }

    @Override
    public Uni<GetRuntimeAssignmentResponse> getRuntimeAssignment(final GetRuntimeAssignmentRequest request) {
        return runtimeService.getRuntimeAssignment(request);
    }

    @Override
    public Uni<FindRuntimeAssignmentResponse> findRuntimeAssignment(final FindRuntimeAssignmentRequest request) {
        return runtimeService.findRuntimeAssignment(request);
    }

    @Override
    public Uni<ViewRuntimeAssignmentsResponse> viewRuntimeAssignments(final ViewRuntimeAssignmentsRequest request) {
        return runtimeService.viewRuntimeAssignments(request);
    }

    @Override
    public Uni<CountRuntimeAssignmentsResponse> countRuntimeAssignments(final CountRuntimeAssignmentsRequest request) {
        return runtimeService.countRuntimeAssignments(request);
    }

    @Override
    public Uni<SyncRuntimeAssignmentResponse> syncRuntimeAssignment(final SyncRuntimeAssignmentRequest request) {
        return runtimeService.syncRuntimeAssignment(request);
    }

    @Override
    public Uni<DeleteRuntimeAssignmentResponse> deleteRuntimeAssignment(final DeleteRuntimeAssignmentRequest request) {
        return runtimeService.deleteRuntimeAssignment(request);
    }

    @Override
    public Uni<InterchangeResponse> interchange(final InterchangeRequest request) {
        return runtimeService.interchange(request);
    }
}
