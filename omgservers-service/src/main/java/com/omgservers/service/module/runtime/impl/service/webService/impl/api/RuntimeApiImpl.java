package com.omgservers.service.module.runtime.impl.service.webService.impl.api;

import com.omgservers.schema.model.internalRole.InternalRoleEnum;
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
import com.omgservers.service.module.runtime.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@RolesAllowed({InternalRoleEnum.Names.SERVICE})
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeApiImpl implements RuntimeApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    public Uni<SyncRuntimeResponse> syncLobbyRuntime(final SyncRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncLobbyRuntime);
    }

    @Override
    public Uni<GetRuntimeResponse> getRuntime(final GetRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getRuntime);
    }

    @Override
    public Uni<DeleteRuntimeResponse> deleteRuntime(final DeleteRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRuntime);
    }

    @Override
    public Uni<SyncRuntimePermissionResponse> syncRuntimePermission(final SyncRuntimePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRuntimePermission);
    }

    @Override
    public Uni<FindRuntimePermissionResponse> findRuntimePermission(final FindRuntimePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findRuntimePermission);
    }

    @Override
    public Uni<ViewRuntimePermissionsResponse> viewRuntimePermissions(final ViewRuntimePermissionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewRuntimePermissions);
    }

    @Override
    public Uni<DeleteRuntimePermissionResponse> deleteRuntimePermission(final DeleteRuntimePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRuntimePermission);
    }

    @Override
    public Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(final ViewRuntimeCommandsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewRuntimeCommands);
    }

    @Override
    public Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(final SyncRuntimeCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRuntimeCommand);
    }

    @Override
    public Uni<SyncClientCommandResponse> syncClientCommand(final SyncClientCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncClientCommand);
    }

    @Override
    public Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(final DeleteRuntimeCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRuntimeCommand);
    }

    @Override
    public Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(final DeleteRuntimeCommandsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRuntimeCommands);
    }

    @Override
    public Uni<GetRuntimeAssignmentResponse> getRuntimeAssignment(final GetRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getRuntimeAssignment);
    }

    @Override
    public Uni<FindRuntimeAssignmentResponse> findRuntimeAssignment(final FindRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findRuntimeAssignment);
    }

    @Override
    public Uni<ViewRuntimeAssignmentsResponse> viewRuntimeAssignments(final ViewRuntimeAssignmentsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewRuntimeAssignments);
    }

    @Override
    public Uni<CountRuntimeAssignmentsResponse> countRuntimeAssignments(final CountRuntimeAssignmentsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::countRuntimeAssignments);
    }

    @Override
    public Uni<SyncRuntimeAssignmentResponse> syncRuntimeAssignment(final SyncRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRuntimeAssignment);
    }

    @Override
    public Uni<UpdateRuntimeAssignmentLastActivityResponse> updateRuntimeAssignmentLastActivity(
            final UpdateRuntimeAssignmentLastActivityRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::updateRuntimeAssignmentLastActivity);
    }

    @Override
    public Uni<DeleteRuntimeAssignmentResponse> deleteRuntimeAssignment(final DeleteRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRuntimeAssignment);
    }

    @Override
    public Uni<GetRuntimePoolServerContainerRefResponse> getRuntimePoolServerContainerRef(
            final GetRuntimePoolServerContainerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getRuntimePoolServerContainerRef);
    }

    @Override
    public Uni<FindRuntimePoolServerContainerRefResponse> findRuntimePoolServerContainerRef(
            final FindRuntimePoolServerContainerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findRuntimePoolServerContainerRef);
    }

    @Override
    public Uni<SyncRuntimePoolServerContainerRefResponse> syncRuntimePoolServerContainerRef(
            final SyncRuntimePoolServerContainerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRuntimePoolServerContainerRef);
    }

    @Override
    public Uni<DeleteRuntimePoolServerContainerRefResponse> deleteRuntimePoolServerContainerRef(
            final DeleteRuntimePoolServerContainerRefRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request,
                webService::deleteRuntimePoolServerContainerRef);
    }

    @Override
    public Uni<InterchangeResponse> interchange(final InterchangeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::interchange);
    }
}
