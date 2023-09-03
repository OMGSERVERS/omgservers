package com.omgservers.module.tenant.impl.service.tenantWebService;

import com.omgservers.dto.tenant.DeleteProjectShardedRequest;
import com.omgservers.dto.tenant.DeleteStageShardedRequest;
import com.omgservers.dto.tenant.DeleteStageShardedResponse;
import com.omgservers.dto.tenant.DeleteTenantShardedRequest;
import com.omgservers.dto.tenant.DeleteVersionShardedRequest;
import com.omgservers.dto.tenant.DeleteVersionShardedResponse;
import com.omgservers.dto.tenant.GetStageVersionIdShardedRequest;
import com.omgservers.dto.tenant.GetStageVersionIdShardedResponse;
import com.omgservers.dto.tenant.GetProjectShardedRequest;
import com.omgservers.dto.tenant.GetProjectShardedResponse;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedResponse;
import com.omgservers.dto.tenant.GetTenantShardedRequest;
import com.omgservers.dto.tenant.GetTenantShardedResponse;
import com.omgservers.dto.tenant.GetVersionBytecodeShardedRequest;
import com.omgservers.dto.tenant.GetVersionBytecodeShardedResponse;
import com.omgservers.dto.tenant.GetVersionConfigShardedRequest;
import com.omgservers.dto.tenant.GetVersionConfigShardedResponse;
import com.omgservers.dto.tenant.GetVersionShardedRequest;
import com.omgservers.dto.tenant.GetVersionShardedResponse;
import com.omgservers.dto.tenant.HasProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.HasProjectPermissionShardedResponse;
import com.omgservers.dto.tenant.HasStagePermissionShardedRequest;
import com.omgservers.dto.tenant.HasStagePermissionShardedResponse;
import com.omgservers.dto.tenant.HasTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.HasTenantPermissionShardedResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedResponse;
import com.omgservers.dto.tenant.SyncProjectShardedRequest;
import com.omgservers.dto.tenant.SyncProjectShardedResponse;
import com.omgservers.dto.tenant.SyncStagePermissionShardedRequest;
import com.omgservers.dto.tenant.SyncStagePermissionShardedResponse;
import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.dto.tenant.SyncStageShardedResponse;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedResponse;
import com.omgservers.dto.tenant.SyncTenantShardedRequest;
import com.omgservers.dto.tenant.SyncTenantShardedResponse;
import com.omgservers.dto.tenant.SyncVersionShardedRequest;
import com.omgservers.dto.tenant.SyncVersionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface TenantWebService {

    Uni<GetTenantShardedResponse> getTenant(GetTenantShardedRequest request);

    Uni<HasTenantPermissionShardedResponse> hasTenantPermission(HasTenantPermissionShardedRequest request);

    Uni<SyncTenantShardedResponse> syncTenant(SyncTenantShardedRequest request);

    Uni<Void> deleteTenant(DeleteTenantShardedRequest request);

    Uni<SyncTenantPermissionShardedResponse> syncTenantPermission(SyncTenantPermissionShardedRequest request);

    Uni<GetProjectShardedResponse> getProject(GetProjectShardedRequest request);

    Uni<SyncProjectShardedResponse> syncProject(SyncProjectShardedRequest request);

    Uni<Void> deleteProject(DeleteProjectShardedRequest request);

    Uni<HasProjectPermissionShardedResponse> hasProjectPermission(HasProjectPermissionShardedRequest request);

    Uni<SyncProjectPermissionShardedResponse> syncProjectPermission(SyncProjectPermissionShardedRequest request);

    Uni<GetStageShardedResponse> getStage(GetStageShardedRequest request);

    Uni<SyncStageShardedResponse> syncStage(SyncStageShardedRequest request);

    Uni<DeleteStageShardedResponse> deleteStage(DeleteStageShardedRequest request);

    Uni<HasStagePermissionShardedResponse> hasStagePermission(HasStagePermissionShardedRequest request);

    Uni<SyncStagePermissionShardedResponse> syncStagePermission(SyncStagePermissionShardedRequest request);

    Uni<GetVersionShardedResponse> getVersion(GetVersionShardedRequest request);

    Uni<SyncVersionShardedResponse> syncVersion(SyncVersionShardedRequest request);

    Uni<DeleteVersionShardedResponse> deleteVersion(DeleteVersionShardedRequest request);

    Uni<GetVersionBytecodeShardedResponse> getBytecode(GetVersionBytecodeShardedRequest request);

    Uni<GetVersionConfigShardedResponse> getVersionConfig(GetVersionConfigShardedRequest request);

    Uni<GetStageVersionIdShardedResponse> getStageVersionId(GetStageVersionIdShardedRequest request);
}
