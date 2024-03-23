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
import com.omgservers.model.internalRole.InternalRoleEnum;
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
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class RuntimeApiImpl implements RuntimeApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRuntimeResponse> syncLobbyRuntime(final SyncRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncLobbyRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetRuntimeResponse> getRuntime(final GetRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRuntimeResponse> deleteRuntime(final DeleteRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRuntimePermissionResponse> syncRuntimePermission(final SyncRuntimePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRuntimePermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindRuntimePermissionResponse> findRuntimePermission(final FindRuntimePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findRuntimePermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewRuntimePermissionsResponse> viewRuntimePermissions(final ViewRuntimePermissionsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewRuntimePermissions);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRuntimePermissionResponse> deleteRuntimePermission(final DeleteRuntimePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRuntimePermission);
    }

    @Override
    public Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(final ViewRuntimeCommandsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewRuntimeCommands);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRuntimeCommandResponse> syncRuntimeCommand(final SyncRuntimeCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRuntimeCommand);
    }

    @Override
    public Uni<SyncClientCommandResponse> syncClientCommand(final SyncClientCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncClientCommand);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(final DeleteRuntimeCommandRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRuntimeCommand);
    }

    @Override
    public Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(final DeleteRuntimeCommandsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRuntimeCommands);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetRuntimeAssignmentResponse> getRuntimeAssignment(final GetRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getRuntimeAssignment);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindRuntimeAssignmentResponse> findRuntimeAssignment(final FindRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findRuntimeAssignment);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewRuntimeAssignmentsResponse> viewRuntimeAssignments(final ViewRuntimeAssignmentsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewRuntimeAssignments);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<CountRuntimeAssignmentsResponse> countRuntimeAssignments(final CountRuntimeAssignmentsRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::countRuntimeAssignments);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncRuntimeAssignmentResponse> syncRuntimeAssignment(final SyncRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncRuntimeAssignment);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteRuntimeAssignmentResponse> deleteRuntimeAssignment(final DeleteRuntimeAssignmentRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteRuntimeAssignment);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<InterchangeResponse> interchange(final InterchangeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::interchange);
    }
}
