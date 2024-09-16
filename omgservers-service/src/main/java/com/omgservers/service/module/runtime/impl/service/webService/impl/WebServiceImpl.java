package com.omgservers.service.module.runtime.impl.service.webService.impl;

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
    public Uni<UpdateRuntimeAssignmentLastActivityResponse> updateRuntimeAssignmentLastActivity(
            final UpdateRuntimeAssignmentLastActivityRequest request) {
        return runtimeService.updateRuntimeAssignmentLastActivity(request);
    }

    @Override
    public Uni<DeleteRuntimeAssignmentResponse> deleteRuntimeAssignment(final DeleteRuntimeAssignmentRequest request) {
        return runtimeService.deleteRuntimeAssignment(request);
    }

    @Override
    public Uni<GetRuntimePoolServerContainerRefResponse> getRuntimePoolServerContainerRef(
            final GetRuntimePoolServerContainerRefRequest request) {
        return runtimeService.getRuntimePoolServerContainerRef(request);
    }

    @Override
    public Uni<FindRuntimePoolServerContainerRefResponse> findRuntimePoolServerContainerRef(
            final FindRuntimePoolServerContainerRefRequest request) {
        return runtimeService.findRuntimePoolServerContainerRef(request);
    }

    @Override
    public Uni<SyncRuntimePoolServerContainerRefResponse> syncRuntimePoolServerContainerRef(
            final SyncRuntimePoolServerContainerRefRequest request) {
        return runtimeService.syncRuntimePoolServerContainerRef(request);
    }

    @Override
    public Uni<DeleteRuntimePoolServerContainerRefResponse> deleteRuntimePoolServerContainerRef(
            final DeleteRuntimePoolServerContainerRefRequest request) {
        return runtimeService.deleteRuntimePoolServerContainerRef(request);
    }

    @Override
    public Uni<InterchangeResponse> interchange(final InterchangeRequest request) {
        return runtimeService.interchange(request);
    }
}
