package com.omgservers.module.tenant.impl.service.webService.impl.api;

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
import com.omgservers.dto.tenant.FindStageVersionIdRequest;
import com.omgservers.dto.tenant.FindStageVersionIdResponse;
import com.omgservers.dto.tenant.FindVersionMatchmakerRequest;
import com.omgservers.dto.tenant.FindVersionMatchmakerResponse;
import com.omgservers.dto.tenant.FindVersionRuntimeRequest;
import com.omgservers.dto.tenant.FindVersionRuntimeResponse;
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
import com.omgservers.dto.tenant.GetVersionMatchmakerRequest;
import com.omgservers.dto.tenant.GetVersionMatchmakerResponse;
import com.omgservers.dto.tenant.GetVersionRequest;
import com.omgservers.dto.tenant.GetVersionResponse;
import com.omgservers.dto.tenant.GetVersionRuntimeRequest;
import com.omgservers.dto.tenant.GetVersionRuntimeResponse;
import com.omgservers.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.dto.tenant.HasStagePermissionRequest;
import com.omgservers.dto.tenant.HasStagePermissionResponse;
import com.omgservers.dto.tenant.HasTenantPermissionRequest;
import com.omgservers.dto.tenant.HasTenantPermissionResponse;
import com.omgservers.dto.tenant.SelectVersionMatchmakerRequest;
import com.omgservers.dto.tenant.SelectVersionMatchmakerResponse;
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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

@Path("/omgservers/tenant-api/v1/request")
public interface TenantApi {

    @PUT
    @Path("/get-tenant")
    Uni<GetTenantResponse> getTenant(GetTenantRequest request);

    @PUT
    @Path("/sync-tenant")
    Uni<SyncTenantResponse> syncTenant(SyncTenantRequest request);

    @PUT
    @Path("/delete-tenant")
    Uni<Void> deleteTenant(DeleteTenantRequest request);

    @PUT
    @Path("/has-tenant-permission")
    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionRequest request);

    @PUT
    @Path("/sync-tenant-permission")
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionRequest request);

    @PUT
    @Path("/get-project")
    Uni<GetProjectResponse> getProject(GetProjectRequest request);

    @PUT
    @Path("/sync-project")
    Uni<SyncProjectResponse> syncProject(SyncProjectRequest request);

    @PUT
    @Path("/delete-project")
    Uni<Void> deleteProject(DeleteProjectRequest request);

    @PUT
    @Path("/has-project-permission")
    Uni<HasProjectPermissionResponse> hasProjectPermission(HasProjectPermissionRequest request);

    @PUT
    @Path("/sync-project-permission")
    Uni<SyncProjectPermissionResponse> syncProjectPermission(SyncProjectPermissionRequest request);

    @PUT
    @Path("/get-stage")
    Uni<GetStageResponse> getStage(GetStageRequest request);

    @PUT
    @Path("/sync-stage")
    Uni<SyncStageResponse> syncStage(SyncStageRequest request);

    @PUT
    @Path("/delete-stage")
    Uni<DeleteStageResponse> deleteStage(DeleteStageRequest request);

    @PUT
    @Path("/has-stage-permission")
    Uni<HasStagePermissionResponse> hasStagePermission(HasStagePermissionRequest request);

    @PUT
    @Path("/sync-stage-permission")
    Uni<SyncStagePermissionResponse> syncStagePermission(SyncStagePermissionRequest request);

    @PUT
    @Path("/get-version")
    Uni<GetVersionResponse> getVersion(GetVersionRequest request);

    @PUT
    @Path("/sync-version")
    Uni<SyncVersionResponse> syncVersion(SyncVersionRequest request);

    @PUT
    @Path("/delete-version")
    Uni<DeleteVersionResponse> deleteVersion(DeleteVersionRequest request);

    @PUT
    @Path("/get-version-bytecode")
    Uni<GetVersionBytecodeResponse> getVersionBytecode(GetVersionBytecodeRequest request);

    @PUT
    @Path("/get-version-config")
    Uni<GetVersionConfigResponse> getVersionConfig(GetVersionConfigRequest request);

    @PUT
    @Path("/get-version-matchmaker")
    Uni<GetVersionMatchmakerResponse> getVersionMatchmaker(GetVersionMatchmakerRequest request);

    @PUT
    @Path("/sync-version-matchmaker")
    Uni<SyncVersionMatchmakerResponse> syncVersionMatchmaker(SyncVersionMatchmakerRequest request);

    @PUT
    @Path("/find-version-matchmaker")
    Uni<FindVersionMatchmakerResponse> findVersionMatchmaker(FindVersionMatchmakerRequest request);

    @PUT
    @Path("/select-version-matchmaker")
    Uni<SelectVersionMatchmakerResponse> selectVersionMatchmaker(SelectVersionMatchmakerRequest request);

    @PUT
    @Path("/view-version-matchmakers")
    Uni<ViewVersionMatchmakersResponse> viewVersionMatchmakers(ViewVersionMatchmakersRequest request);

    @PUT
    @Path("/delete-version-matchmaker")
    Uni<DeleteVersionMatchmakerResponse> deleteVersionMatchmaker(DeleteVersionMatchmakerRequest request);

    @PUT
    @Path("/get-version-runtime")
    Uni<GetVersionRuntimeResponse> getVersionRuntime(GetVersionRuntimeRequest request);

    @PUT
    @Path("/sync-version-runtime")
    Uni<SyncVersionRuntimeResponse> syncVersionRuntime(SyncVersionRuntimeRequest request);

    @PUT
    @Path("/find-version-runtime")
    Uni<FindVersionRuntimeResponse> findVersionRuntime(FindVersionRuntimeRequest request);

    @PUT
    @Path("/select-version-runtime")
    Uni<SelectVersionRuntimeResponse> selectVersionRuntime(SelectVersionRuntimeRequest request);

    @PUT
    @Path("/view-version-runtimes")
    Uni<ViewVersionRuntimesResponse> viewVersionRuntimes(ViewVersionRuntimesRequest request);

    @PUT
    @Path("/delete-version-runtime")
    Uni<DeleteVersionRuntimeResponse> deleteVersionRuntime(DeleteVersionRuntimeRequest request);

    @PUT
    @Path("/find-stage-version-id")
    Uni<FindStageVersionIdResponse> findStageVersionId(FindStageVersionIdRequest request);
}
