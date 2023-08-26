package com.omgservers.module.tenant.impl.service.tenantWebService.impl.serviceApi;

import com.omgservers.dto.tenantModule.DeleteProjectShardRequest;
import com.omgservers.dto.tenantModule.GetProjectShardRequest;
import com.omgservers.dto.tenantModule.GetTenantShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectShardRequest;
import com.omgservers.dto.tenantModule.SyncStageShardRequest;
import com.omgservers.module.tenant.impl.service.tenantWebService.TenantWebService;
import com.omgservers.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.dto.tenantModule.DeleteStageShardRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.DeleteTenantShardRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.GetStageShardRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionShardRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionShardRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncTenantShardRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncTenantResponse;
import com.omgservers.model.internalRole.InternalRoleEnum;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class TenantServiceApiImpl implements TenantServiceApi {

    final HandleApiRequestOperation handleApiRequestOperation;
    final TenantWebService tenantWebService;

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetTenantResponse> getTenant(GetTenantShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::getTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncTenantResponse> syncTenant(SyncTenantShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> deleteTenant(DeleteTenantShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::deleteTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::hasTenantPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncTenantPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetProjectInternalResponse> getProject(GetProjectShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::getProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncProjectInternalResponse> syncProject(SyncProjectShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> deleteProject(DeleteProjectShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::deleteProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::hasProjectPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncProjectPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetStageInternalResponse> getStage(GetStageShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::getStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncStageInternalResponse> syncStage(SyncStageShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteStageInternalResponse> deleteStage(DeleteStageShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::deleteStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::hasStagePermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionShardRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncStagePermission);
    }
}
