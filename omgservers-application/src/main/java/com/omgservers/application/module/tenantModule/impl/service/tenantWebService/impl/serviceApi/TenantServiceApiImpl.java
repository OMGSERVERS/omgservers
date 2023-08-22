package com.omgservers.application.module.tenantModule.impl.service.tenantWebService.impl.serviceApi;

import com.omgservers.application.module.securityModule.model.InternalRoleEnum;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.DeleteProjectInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.GetProjectInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.HasProjectPermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.SyncProjectInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.request.SyncProjectPermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.GetProjectInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.HasProjectPermissionInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.SyncProjectInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.projectInternalService.response.SyncProjectPermissionInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.DeleteStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.GetStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.HasStagePermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStageInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.request.SyncStagePermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.DeleteStageInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.GetStageInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.HasStagePermissionInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.SyncStageInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.stageInternalService.response.SyncStagePermissionInternalResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.DeleteTenantInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.GetTenantInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.HasTenantPermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.SyncTenantInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.request.SyncTenantPermissionInternalRequest;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.GetTenantResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.HasTenantPermissionResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantPermissionResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantInternalService.response.SyncTenantResponse;
import com.omgservers.application.module.tenantModule.impl.service.tenantWebService.TenantWebService;
import com.omgservers.application.operation.handleApiRequestOperation.HandleApiRequestOperation;
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
