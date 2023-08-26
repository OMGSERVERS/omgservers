package com.omgservers.application.module.tenantModule.impl.service.tenantWebService.impl.serviceApi;

import com.omgservers.application.module.tenantModule.impl.service.tenantWebService.TenantWebService;
import com.omgservers.base.operation.handleApiRequest.HandleApiRequestOperation;
import com.omgservers.dto.tenantModule.DeleteProjectRoutedRequest;
import com.omgservers.dto.tenantModule.DeleteStageRoutedRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.DeleteTenantRoutedRequest;
import com.omgservers.dto.tenantModule.GetProjectRoutedRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.GetStageRoutedRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.GetTenantRoutedRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncProjectRoutedRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionRoutedRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionRoutedRequest;
import com.omgservers.dto.tenantModule.SyncStageRoutedRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncTenantRoutedRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionRoutedRequest;
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
    public Uni<GetTenantResponse> getTenant(GetTenantRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::getTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncTenantResponse> syncTenant(SyncTenantRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> deleteTenant(DeleteTenantRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::deleteTenant);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::hasTenantPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncTenantPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetProjectInternalResponse> getProject(GetProjectRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::getProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncProjectInternalResponse> syncProject(SyncProjectRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<Void> deleteProject(DeleteProjectRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::deleteProject);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::hasProjectPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncProjectPermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<GetStageInternalResponse> getStage(GetStageRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::getStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncStageInternalResponse> syncStage(SyncStageRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<DeleteStageInternalResponse> deleteStage(DeleteStageRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::deleteStage);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::hasStagePermission);
    }

    @Override
    @RolesAllowed({InternalRoleEnum.Names.SERVICE})
    public Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionRoutedRequest request) {
        return handleApiRequestOperation.handleApiRequest(log, request, tenantWebService::syncStagePermission);
    }
}
