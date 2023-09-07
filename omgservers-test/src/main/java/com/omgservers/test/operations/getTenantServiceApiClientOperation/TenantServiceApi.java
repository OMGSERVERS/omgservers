package com.omgservers.test.operations.getTenantServiceApiClientOperation;

import com.omgservers.dto.tenant.DeleteProjectShardedRequest;
import com.omgservers.dto.tenant.DeleteStageShardedRequest;
import com.omgservers.dto.tenant.DeleteStageShardedResponse;
import com.omgservers.dto.tenant.DeleteTenantShardedRequest;
import com.omgservers.dto.tenant.GetProjectShardedRequest;
import com.omgservers.dto.tenant.GetProjectShardedResponse;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageShardedResponse;
import com.omgservers.dto.tenant.GetTenantShardedRequest;
import com.omgservers.dto.tenant.GetTenantShardedResponse;
import com.omgservers.dto.tenant.HasProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.HasProjectPermissionShardedResponse;
import com.omgservers.dto.tenant.HasStagePermissionShardedRequest;
import com.omgservers.dto.tenant.HasStagePermissionShardedResponse;
import com.omgservers.dto.tenant.HasTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.HasTenantPermissionShardedResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncProjectShardedRequest;
import com.omgservers.dto.tenant.SyncProjectShardedResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedResponse;
import com.omgservers.dto.tenant.SyncStagePermissionShardedRequest;
import com.omgservers.dto.tenant.SyncStageShardedRequest;
import com.omgservers.dto.tenant.SyncStageShardedResponse;
import com.omgservers.dto.tenant.SyncStagePermissionShardedResponse;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncTenantShardedRequest;
import com.omgservers.dto.tenant.SyncTenantPermissionShardedResponse;
import com.omgservers.dto.tenant.SyncTenantShardedResponse;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/tenant-api/v1/request")
public interface TenantServiceApi {

    @PUT
    @Path("/get-tenant")
    Uni<GetTenantShardedResponse> getTenant(GetTenantShardedRequest request);

    default GetTenantShardedResponse getTenant(long timeout, GetTenantShardedRequest request) {
        return getTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-tenant")
    Uni<SyncTenantShardedResponse> syncTenant(SyncTenantShardedRequest request);

    default SyncTenantShardedResponse syncTenant(long timeout, SyncTenantShardedRequest request) {
        return syncTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-tenant")
    Uni<Void> deleteTenant(DeleteTenantShardedRequest request);

    default void deleteTenant(long timeout, DeleteTenantShardedRequest request) {
        deleteTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-tenant-permission")
    Uni<HasTenantPermissionShardedResponse> hasTenantPermission(HasTenantPermissionShardedRequest request);

    default HasTenantPermissionShardedResponse hasTenantPermission(long timeout, HasTenantPermissionShardedRequest request) {
        return hasTenantPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-tenant-permission")
    Uni<SyncTenantPermissionShardedResponse> syncTenantPermission(SyncTenantPermissionShardedRequest request);

    default SyncTenantPermissionShardedResponse syncTenantPermission(long timeout, SyncTenantPermissionShardedRequest request) {
        return syncTenantPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-project")
    Uni<GetProjectShardedResponse> getProject(GetProjectShardedRequest request);

    default GetProjectShardedResponse getProject(long timeout, GetProjectShardedRequest request) {
        return getProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-project")
    Uni<SyncProjectShardedResponse> syncProject(SyncProjectShardedRequest request);

    default SyncProjectShardedResponse syncProject(long timeout, SyncProjectShardedRequest request) {
        return syncProject(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-project")
    Uni<Void> deleteProject(DeleteProjectShardedRequest request);

    default void deleteProject(long timeout, DeleteProjectShardedRequest request) {
        deleteProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-project-permission")
    Uni<HasProjectPermissionShardedResponse> hasProjectPermission(HasProjectPermissionShardedRequest request);

    default HasProjectPermissionShardedResponse hasProjectPermission(long timeout, HasProjectPermissionShardedRequest request) {
        return hasProjectPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-project-permission")
    Uni<SyncProjectPermissionShardedResponse> syncProjectPermission(SyncProjectPermissionShardedRequest request);

    default SyncProjectPermissionShardedResponse syncProjectPermission(long timeout, SyncProjectPermissionShardedRequest request) {
        return syncProjectPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-stage")
    Uni<GetStageShardedResponse> getStage(GetStageShardedRequest request);

    default GetStageShardedResponse getStage(long timeout, GetStageShardedRequest request) {
        return getStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-stage")
    Uni<SyncStageShardedResponse> syncStage(SyncStageShardedRequest request);

    default SyncStageShardedResponse syncStage(long timeout, SyncStageShardedRequest request) {
        return syncStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-stage")
    Uni<DeleteStageShardedResponse> deleteStage(DeleteStageShardedRequest request);

    default DeleteStageShardedResponse deleteStage(long timeout, DeleteStageShardedRequest request) {
        return deleteStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-stage-permission")
    Uni<HasStagePermissionShardedResponse> hasStagePermission(HasStagePermissionShardedRequest request);

    default HasStagePermissionShardedResponse hasStagePermission(long timeout, HasStagePermissionShardedRequest request) {
        return hasStagePermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-stage-permission")
    Uni<SyncStagePermissionShardedResponse> syncStagePermission(SyncStagePermissionShardedRequest request);

    default SyncStagePermissionShardedResponse syncStagePermission(long timeout, SyncStagePermissionShardedRequest request) {
        return syncStagePermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
