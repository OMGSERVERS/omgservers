package com.omgservers.module.tenant.impl.service.webService;

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
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetTenantResponse> getTenant(GetTenantRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRequest request);

    Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request);

    Uni<Void> deleteTenant(DeleteTenantRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request);

    Uni<GetProjectResponse> getProject(GetProjectRequest request);

    Uni<SyncProjectShardedResponse> syncProject(SyncProjectRequest request);

    Uni<Void> deleteProject(DeleteProjectRequest request);

    Uni<HasProjectPermissionResponse> hasProjectPermission(HasProjectPermissionRequest request);

    Uni<SyncProjectPermissionResponse> syncProjectPermission(SyncProjectPermissionRequest request);

    Uni<GetStageResponse> getStage(GetStageRequest request);

    Uni<SyncStageResponse> syncStage(SyncStageRequest request);

    Uni<DeleteStageResponse> deleteStage(DeleteStageRequest request);

    Uni<HasStagePermissionResponse> hasStagePermission(HasStagePermissionRequest request);

    Uni<SyncStagePermissionResponse> syncStagePermission(SyncStagePermissionRequest request);

    Uni<GetVersionResponse> getVersion(GetVersionRequest request);

    Uni<SyncVersionResponse> syncVersion(SyncVersionRequest request);

    Uni<DeleteVersionResponse> deleteVersion(DeleteVersionRequest request);

    Uni<GetVersionBytecodeResponse> getBytecode(GetVersionBytecodeRequest request);

    Uni<GetVersionConfigResponse> getVersionConfig(GetVersionConfigRequest request);

    Uni<GetStageVersionIdResponse> getStageVersionId(GetStageVersionIdRequest request);
}
