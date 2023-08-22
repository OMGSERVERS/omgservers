package com.omgservers.application.module.tenantModule.impl.service.tenantWebService;

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
import io.smallrye.mutiny.Uni;

public interface TenantWebService {

    Uni<GetTenantResponse> getTenant(GetTenantInternalRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionInternalRequest request);

    Uni<SyncTenantResponse> syncTenant(SyncTenantInternalRequest request);

    Uni<Void> deleteTenant(DeleteTenantInternalRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionInternalRequest request);

    Uni<GetProjectInternalResponse> getProject(GetProjectInternalRequest request);

    Uni<SyncProjectInternalResponse> syncProject(SyncProjectInternalRequest request);

    Uni<Void> deleteProject(DeleteProjectInternalRequest request);

    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionInternalRequest request);

    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionInternalRequest request);

    Uni<GetStageInternalResponse> getStage(GetStageInternalRequest request);

    Uni<SyncStageInternalResponse> syncStage(SyncStageInternalRequest request);

    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageInternalRequest request);

    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionInternalRequest request);

    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionInternalRequest request);
}
