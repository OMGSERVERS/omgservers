package com.omgservers.application.module.tenantModule.impl.service.tenantWebService;

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
