package com.omgservers.application.module.tenantModule.impl.service.tenantWebService.impl.serviceApi;

import com.omgservers.application.module.tenantModule.impl.service.tenantWebService.TenantWebService;
import com.omgservers.base.impl.operation.handleApiRequestOperation.HandleApiRequestOperation;
import com.omgservers.dto.tenantModule.DeleteProjectInternalRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.DeleteTenantInternalRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.GetStageInternalRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.GetTenantInternalRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionInternalRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncProjectInternalRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStageInternalRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalRequest;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncTenantInternalRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionInternalRequest;
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
    public Uni<GetTenantResponse> getTenant(GetTenantInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::getTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncTenantResponse> syncTenant(SyncTenantInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> deleteTenant(DeleteTenantInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::deleteTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::hasTenantPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncTenantPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetProjectInternalResponse> getProject(GetProjectInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::getProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncProjectInternalResponse> syncProject(SyncProjectInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> deleteProject(DeleteProjectInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::deleteProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::hasProjectPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncProjectPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetStageInternalResponse> getStage(GetStageInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::getStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncStageInternalResponse> syncStage(SyncStageInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteStageInternalResponse> deleteStage(DeleteStageInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::deleteStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::hasStagePermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionInternalRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncStagePermission);
    }
}
