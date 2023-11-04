package com.omgservers.service.module.tenant.impl.service.webService;

import com.omgservers.model.dto.tenant.DeleteProjectRequest;
import com.omgservers.model.dto.tenant.DeleteStageRequest;
import com.omgservers.model.dto.tenant.DeleteStageResponse;
import com.omgservers.model.dto.tenant.DeleteTenantRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.DeleteVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRequest;
import com.omgservers.model.dto.tenant.DeleteVersionResponse;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.DeleteVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.FindStageVersionIdRequest;
import com.omgservers.model.dto.tenant.FindStageVersionIdResponse;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.FindVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.FindVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.GetProjectRequest;
import com.omgservers.model.dto.tenant.GetProjectResponse;
import com.omgservers.model.dto.tenant.GetStageRequest;
import com.omgservers.model.dto.tenant.GetStageResponse;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.GetVersionConfigRequest;
import com.omgservers.model.dto.tenant.GetVersionConfigResponse;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.GetVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.GetVersionRequest;
import com.omgservers.model.dto.tenant.GetVersionResponse;
import com.omgservers.model.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.GetVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.model.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.model.dto.tenant.HasStagePermissionRequest;
import com.omgservers.model.dto.tenant.HasStagePermissionResponse;
import com.omgservers.model.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.model.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SelectVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SelectVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.model.dto.tenant.SyncProjectPermissionResponse;
import com.omgservers.model.dto.tenant.SyncProjectRequest;
import com.omgservers.model.dto.tenant.SyncProjectResponse;
import com.omgservers.model.dto.tenant.SyncStagePermissionRequest;
import com.omgservers.model.dto.tenant.SyncStagePermissionResponse;
import com.omgservers.model.dto.tenant.SyncStageRequest;
import com.omgservers.model.dto.tenant.SyncStageResponse;
import com.omgservers.model.dto.tenant.SyncTenantPermissionRequest;
import com.omgservers.model.dto.tenant.SyncTenantPermissionResponse;
import com.omgservers.model.dto.tenant.SyncTenantRequest;
import com.omgservers.model.dto.tenant.SyncTenantResponse;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerRequest;
import com.omgservers.model.dto.tenant.SyncVersionMatchmakerResponse;
import com.omgservers.model.dto.tenant.SyncVersionRequest;
import com.omgservers.model.dto.tenant.SyncVersionResponse;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeRequest;
import com.omgservers.model.dto.tenant.SyncVersionRuntimeResponse;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersResponse;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesRequest;
import com.omgservers.model.dto.tenant.ViewVersionRuntimesResponse;
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

    Uni<GetVersionConfigResponse> getVersionConfig(GetVersionConfigRequest request);

    Uni<GetVersionMatchmakerResponse> getVersionMatchmaker(GetVersionMatchmakerRequest request);

    Uni<SyncVersionMatchmakerResponse> syncVersionMatchmaker(SyncVersionMatchmakerRequest request);

    Uni<FindVersionMatchmakerResponse> findVersionMatchmaker(FindVersionMatchmakerRequest request);

    Uni<SelectVersionMatchmakerResponse> selectVersionMatchmaker(SelectVersionMatchmakerRequest request);

    Uni<ViewVersionMatchmakersResponse> viewVersionMatchmakers(ViewVersionMatchmakersRequest request);

    Uni<DeleteVersionMatchmakerResponse> deleteVersionMatchmaker(DeleteVersionMatchmakerRequest request);

    Uni<GetVersionRuntimeResponse> getVersionRuntime(GetVersionRuntimeRequest request);

    Uni<SyncVersionRuntimeResponse> syncVersionRuntime(SyncVersionRuntimeRequest request);

    Uni<FindVersionRuntimeResponse> findVersionRuntime(FindVersionRuntimeRequest request);

    Uni<SelectVersionRuntimeResponse> selectVersionRuntime(SelectVersionRuntimeRequest request);

    Uni<ViewVersionRuntimesResponse> viewVersionRuntimes(ViewVersionRuntimesRequest request);

    Uni<DeleteVersionRuntimeResponse> deleteVersionRuntime(DeleteVersionRuntimeRequest request);

    Uni<FindStageVersionIdResponse> findStageVersionId(FindStageVersionIdRequest request);
}
