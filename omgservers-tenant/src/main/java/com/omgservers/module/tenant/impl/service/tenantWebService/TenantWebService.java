package com.omgservers.module.tenant.impl.service.tenantWebService;

import com.omgservers.dto.tenantModule.DeleteProjectShardRequest;
import com.omgservers.dto.tenantModule.DeleteStageShardRequest;
import com.omgservers.dto.tenantModule.DeleteStageInternalResponse;
import com.omgservers.dto.tenantModule.DeleteTenantShardRequest;
import com.omgservers.dto.tenantModule.GetProjectShardRequest;
import com.omgservers.dto.tenantModule.GetProjectInternalResponse;
import com.omgservers.dto.tenantModule.GetStageShardRequest;
import com.omgservers.dto.tenantModule.GetStageInternalResponse;
import com.omgservers.dto.tenantModule.GetTenantShardRequest;
import com.omgservers.dto.tenantModule.GetTenantResponse;
import com.omgservers.dto.tenantModule.HasProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasStagePermissionShardRequest;
import com.omgservers.dto.tenantModule.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.HasTenantPermissionShardRequest;
import com.omgservers.dto.tenantModule.HasTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncProjectShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectInternalResponse;
import com.omgservers.dto.tenantModule.SyncProjectPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncProjectPermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncStageShardRequest;
import com.omgservers.dto.tenantModule.SyncStageInternalResponse;
import com.omgservers.dto.tenantModule.SyncStagePermissionInternalResponse;
import com.omgservers.dto.tenantModule.SyncTenantShardRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionShardRequest;
import com.omgservers.dto.tenantModule.SyncTenantPermissionResponse;
import com.omgservers.dto.tenantModule.SyncTenantResponse;
import io.smallrye.mutiny.Uni;

public interface TenantWebService {

    Uni<GetTenantResponse> getTenant(GetTenantShardRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionShardRequest request);

    Uni<SyncTenantResponse> syncTenant(SyncTenantShardRequest request);

    Uni<Void> deleteTenant(DeleteTenantShardRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionShardRequest request);

    Uni<GetProjectInternalResponse> getProject(GetProjectShardRequest request);

    Uni<SyncProjectInternalResponse> syncProject(SyncProjectShardRequest request);

    Uni<Void> deleteProject(DeleteProjectShardRequest request);

    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionShardRequest request);

    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionShardRequest request);

    Uni<GetStageInternalResponse> getStage(GetStageShardRequest request);

    Uni<SyncStageInternalResponse> syncStage(SyncStageShardRequest request);

    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageShardRequest request);

    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionShardRequest request);

    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionShardRequest request);
}
