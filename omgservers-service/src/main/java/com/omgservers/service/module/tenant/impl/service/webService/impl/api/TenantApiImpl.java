package com.omgservers.service.module.tenant.impl.service.webService.impl.api;

import com.omgservers.model.dto.tenant.DeleteProjectRequest;
import com.omgservers.model.dto.tenant.DeleteStageRequest;
import com.omgservers.model.dto.tenant.DeleteStageResponse;
import com.omgservers.model.dto.tenant.DeleteTenantRequest;
import com.omgservers.model.dto.tenant.DeleteTenantResponse;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRequest;
import com.omgservers.model.dto.tenant.DeleteVersionResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.FindStageVersionIdRequest;
import com.omgservers.model.dto.tenant.FindStageVersionIdResponse;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.FindVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.GetProjectRequest;
import com.omgservers.model.dto.tenant.GetProjectResponse;
import com.omgservers.model.dto.tenant.GetStageRequest;
import com.omgservers.model.dto.tenant.GetStageResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.GetVersionConfigRequest;
import com.omgservers.model.dto.tenant.GetVersionConfigResponse;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.GetVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.model.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.model.dto.tenant.HasStagePermissionRequest;
import com.omgservers.model.dto.tenant.HasStagePermissionResponse;
import com.omgservers.model.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.model.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.model.dto.tenant.SyncProjectPermissionResponse;
import com.omgservers.model.dto.tenant.SyncProjectRequest;
import com.omgservers.model.dto.tenant.SyncProjectResponse;
import com.omgservers.model.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.model.dto.tenant.SyncStagePermissionResponse;
import com.omgservers.model.dto.tenant.SyncStageRequest;
import com.omgservers.model.dto.tenant.SyncStageResponse;
import com.omgservers.model.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.model.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SyncTenantRequest;
import com.omgservers.model.dto.tenant.SyncTenantResponse;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.SyncVersionRequest;
import com.omgservers.model.dto.tenant.SyncVersionResponse;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersResponse;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesRequest;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.service.module.tenant.impl.service.webService.WebService;
import com.omgservers.service.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TenantApiImpl implements TenantApi {

    final WebService webService;

    final HandleApiRequestOperation handleApiRequestOperation;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetTenantResponse> getTenant(final GetTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncTenantResponse> syncTenant(final SyncTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteTenantResponse> deleteTenant(final DeleteTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasTenantPermissionResponse> hasTenantPermission(final HasTenantPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::hasTenantPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(final SyncTenantPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetProjectResponse> getProject(final GetProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncProjectResponse> syncProject(final SyncProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> deleteProject(final DeleteProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasProjectPermissionResponse> hasProjectPermission(final HasProjectPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::hasProjectPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncProjectPermissionResponse> syncProjectPermission(final SyncProjectPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncProjectPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetStageResponse> getStage(final GetStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncStageResponse> syncStage(final SyncStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteStageResponse> deleteStage(final DeleteStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasStagePermissionResponse> hasStagePermission(final HasStagePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::hasStagePermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncStagePermissionResponse> syncStagePermission(final SyncStagePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncStagePermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetVersionResponse> getVersion(final GetVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncVersionResponse> syncVersion(final SyncVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteVersionResponse> deleteVersion(final DeleteVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetVersionConfigResponse> getVersionConfig(final GetVersionConfigRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersionConfig);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetVersionMatchmakerResponse> getVersionMatchmaker(GetVersionMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersionMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncVersionMatchmakerResponse> syncVersionMatchmaker(final SyncVersionMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncVersionMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindVersionMatchmakerResponse> findVersionMatchmaker(final FindVersionMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findVersionMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SelectVersionMatchmakerResponse> selectVersionMatchmaker(final SelectVersionMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::selectVersionMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewVersionMatchmakersResponse> viewVersionMatchmakers(final ViewVersionMatchmakersRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewVersionMatchmakers);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteVersionMatchmakerResponse> deleteVersionMatchmaker(final DeleteVersionMatchmakerRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteVersionMatchmaker);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetVersionRuntimeResponse> getVersionRuntime(final GetVersionRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersionRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncVersionRuntimeResponse> syncVersionRuntime(final SyncVersionRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncVersionRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindVersionRuntimeResponse> findVersionRuntime(final FindVersionRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findVersionRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SelectVersionRuntimeResponse> selectVersionRuntime(SelectVersionRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::selectVersionRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<ViewVersionRuntimesResponse> viewVersionRuntimes(final ViewVersionRuntimesRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::viewVersionRuntimes);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteVersionRuntimeResponse> deleteVersionRuntime(final DeleteVersionRuntimeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteVersionRuntime);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<FindStageVersionIdResponse> findStageVersionId(final FindStageVersionIdRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::findStageVersionId);
    }
}
