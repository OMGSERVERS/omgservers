package com.omgservers.module.tenant.impl.service.webService.impl.api;

import com.omgservers.dto.tenant.DeleteProjectRequest;
import com.omgservers.dto.tenant.DeleteStageRequest;
import com.omgservers.dto.tenant.DeleteStageResponse;
import com.omgservers.dto.tenant.DeleteTenantRequest;
import com.omgservers.dto.tenant.DeleteVersionRequest;
import com.omgservers.dto.tenant.DeleteVersionResponse;
import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
import com.omgservers.dto.tenant.GetProjectRequest;
import com.omgservers.dto.tenant.GetProjectResponse;
import com.omgservers.dto.tenant.GetStageRequest;
import com.omgservers.dto.tenant.GetStageResponse;
import com.omgservers.dto.tenant.GetTenantRequest;
import com.omgservers.dto.tenant.GetTenantResponse;
import com.omgservers.dto.tenant.GetVersionBytecodeRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeResponse;
import com.omgservers.dto.tenant.GetVersionConfigRequest;
import com.omgservers.dto.tenant.GetVersionConfigResponse;
import com.omgservers.dto.tenant.GetVersionRequest;
import com.omgservers.dto.tenant.GetVersionResponse;
import com.omgservers.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.dto.tenant.HasStagePermissionRequest;
import com.omgservers.dto.tenant.HasStagePermissionResponse;
import com.omgservers.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionResponse;
import com.omgservers.dto.tenant.SyncProjectRequest;
import com.omgservers.dto.tenant.SyncProjectShardedResponse;
import com.omgservers.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.dto.tenant.SyncStagePermissionResponse;
import com.omgservers.dto.tenant.SyncStageRequest;
import com.omgservers.dto.tenant.SyncStageResponse;
import com.omgservers.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncTenantRequest;
import com.omgservers.dto.tenant.SyncTenantResponse;
import com.omgservers.dto.tenant.SyncVersionRequest;
import com.omgservers.dto.tenant.SyncVersionResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import com.omgservers.module.tenant.impl.service.webService.WebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TenantApiImpl implements TenantApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final WebService webService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetTenantResponse> getTenant(GetTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> deleteTenant(DeleteTenantRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::hasTenantPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncTenantPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetProjectResponse> getProject(GetProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncProjectShardedResponse> syncProject(SyncProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> deleteProject(DeleteProjectRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasProjectPermissionResponse> hasProjectPermission(HasProjectPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::hasProjectPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncProjectPermissionResponse> syncProjectPermission(SyncProjectPermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncProjectPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetStageResponse> getStage(GetStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncStageResponse> syncStage(SyncStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteStageResponse> deleteStage(DeleteStageRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasStagePermissionResponse> hasStagePermission(HasStagePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::hasStagePermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncStagePermissionResponse> syncStagePermission(SyncStagePermissionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncStagePermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetVersionResponse> getVersion(GetVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncVersionResponse> syncVersion(SyncVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::syncVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteVersionResponse> deleteVersion(DeleteVersionRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::deleteVersion);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetVersionBytecodeResponse> getVersionBytecode(GetVersionBytecodeRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getBytecode);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetVersionConfigResponse> getVersionConfig(GetVersionConfigRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getVersionConfig);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetStageVersionIdResponse> getStageVersionId(GetStageVersionIdRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, webService::getStageVersionId);
    }
}
