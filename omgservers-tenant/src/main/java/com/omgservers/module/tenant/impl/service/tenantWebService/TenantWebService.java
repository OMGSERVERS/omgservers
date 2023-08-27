package com.omgservers.module.tenant.impl.service.tenantWebService;

import com.omgservers.dto.tenant.DeleteProjectShardedRequest;
import com.omgservers.dto.tenant.DeleteStageShardedRequest;
import com.omgservers.dto.tenant.DeleteStageInternalResponse;
import com.omgservers.dto.tenant.DeleteTenantShardedRequest;
import com.omgservers.dto.tenant.GetProjectShardedRequest;
import com.omgservers.dto.tenant.GetProjectInternalResponse;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageInternalResponse;
import com.omgservers.dto.tenant.GetTenantShardedRequest;
import com.omgservers.dto.tenant.GetTenantResponse;
import com.omgservers.dto.tenant.HasProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenant.HasStagePermissionShardedRequest;
import com.omgservers.dto.tenant.HasStagePermissionInternalResponse;
import com.omgservers.dto.tenant.HasTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncProjectShardedRequest;
import com.omgservers.dto.tenant.SyncProjectInternalResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionInternalResponse;
import com.omgservers.dto.tenant.SyncStagePermissionShardedRequest;
import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.dto.tenant.SyncStageInternalResponse;
import com.omgservers.dto.tenant.SyncStagePermissionInternalResponse;
import com.omgservers.dto.tenant.SyncTenantShardedRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncTenantResponse;
import io.smallrye.mutiny.Uni;

public interface TenantWebService {

    Uni<GetTenantResponse> getTenant(GetTenantShardedRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionShardedRequest request);

    Uni<SyncTenantResponse> syncTenant(SyncTenantShardedRequest request);

    Uni<Void> deleteTenant(DeleteTenantShardedRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionShardedRequest request);

    Uni<GetProjectInternalResponse> getProject(GetProjectShardedRequest request);

    Uni<SyncProjectInternalResponse> syncProject(SyncProjectShardedRequest request);

    Uni<Void> deleteProject(DeleteProjectShardedRequest request);

    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionShardedRequest request);

    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionShardedRequest request);

    Uni<GetStageInternalResponse> getStage(GetStageShardedRequest request);

    Uni<SyncStageInternalResponse> syncStage(SyncStageShardedRequest request);

    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageShardedRequest request);

    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionShardedRequest request);

    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionShardedRequest request);
}
