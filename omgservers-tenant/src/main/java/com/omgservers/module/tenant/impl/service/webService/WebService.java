package com.omgservers.module.tenant.impl.service.webService;

import com.omgservers.dto.tenant.DeleteProjectRequest;
import com.omgservers.dto.tenant.DeleteStageRequest;
import com.omgservers.dto.tenant.DeleteStageResponse;
import com.omgservers.dto.tenant.DeleteTenantRequest;
import com.omgservers.dto.tenant.DeleteVersionMatchmakerRequest;
import com.omgservers.dto.tenant.DeleteVersionMatchmakerResponse;
import com.omgservers.dto.tenant.DeleteVersionRequest;
import com.omgservers.dto.tenant.DeleteVersionResponse;
import com.omgservers.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.dto.tenant.DeleteVersionRuntimeResponse;
import com.omgservers.dto.tenant.FindVersionMatchmakerRequest;
import com.omgservers.dto.tenant.FindVersionMatchmakerResponse;
import com.omgservers.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.dto.tenant.FindVersionRuntimeResponse;
import com.omgservers.dto.tenant.GetProjectRequest;
import com.omgservers.dto.tenant.GetProjectResponse;
import com.omgservers.dto.tenant.GetStageRequest;
import com.omgservers.dto.tenant.GetStageResponse;
import com.omgservers.dto.tenant.FindStageVersionIdRequest;
import com.omgservers.dto.tenant.FindStageVersionIdResponse;
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
import com.omgservers.dto.tenant.SelectVersionRuntimeRequest;
import com.omgservers.dto.tenant.SelectVersionRuntimeResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionResponse;
import com.omgservers.dto.tenant.SyncProjectRequest;
import com.omgservers.dto.tenant.SyncProjectResponse;
import com.omgservers.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.dto.tenant.SyncStagePermissionResponse;
import com.omgservers.dto.tenant.SyncStageRequest;
import com.omgservers.dto.tenant.SyncStageResponse;
import com.omgservers.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.dto.tenant.SyncTenantRequest;
import com.omgservers.dto.tenant.SyncTenantResponse;
import com.omgservers.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.dto.tenant.SyncVersionMatchmakerResponse;
import com.omgservers.dto.tenant.SyncVersionRequest;
import com.omgservers.dto.tenant.SyncVersionResponse;
import com.omgservers.dto.tenant.SyncVersionRuntimeRequest;
import com.omgservers.dto.tenant.SyncVersionRuntimeResponse;
import com.omgservers.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.dto.tenant.ViewVersionMatchmakersResponse;
import com.omgservers.dto.tenant.ViewVersionRuntimesRequest;
import com.omgservers.dto.tenant.ViewVersionRuntimesResponse;
import io.smallrye.mutiny.Uni;

public interface WebService {

    Uni<GetTenantResponse> getTenant(GetTenantRequest request);

    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRequest request);

    Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request);

    Uni<Void> deleteTenant(DeleteTenantRequest request);

    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request);

    Uni<GetProjectResponse> getProject(GetProjectRequest request);

    Uni<SyncProjectResponse> syncProject(SyncProjectRequest request);

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

    Uni<SyncVersionMatchmakerResponse> syncVersionMatchmaker(SyncVersionMatchmakerRequest request);

    Uni<FindVersionMatchmakerResponse> findVersionMatchmaker(FindVersionMatchmakerRequest request);

    Uni<ViewVersionMatchmakersResponse> viewVersionMatchmakers(ViewVersionMatchmakersRequest request);

    Uni<DeleteVersionMatchmakerResponse> deleteVersionMatchmaker(DeleteVersionMatchmakerRequest request);

    Uni<SyncVersionRuntimeResponse> syncVersionRuntime(SyncVersionRuntimeRequest request);

    Uni<FindVersionRuntimeResponse> findVersionRuntime(FindVersionRuntimeRequest request);

    Uni<SelectVersionRuntimeResponse> selectVersionRuntime(SelectVersionRuntimeRequest request);

    Uni<ViewVersionRuntimesResponse> viewVersionRuntimes(ViewVersionRuntimesRequest request);

    Uni<DeleteVersionRuntimeResponse> deleteVersionRuntime(DeleteVersionRuntimeRequest request);

    Uni<FindStageVersionIdResponse> findStageVersionId(FindStageVersionIdRequest request);
}
