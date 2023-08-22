package com.omgservers.application.module.tenantModule.impl.service.tenantWebService.impl.serviceApi;

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
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;

import java.time.Duration;

@Path("/omgservers/service-api/tenant-api/v1/request")
public interface TenantServiceApi {

    @PUT
    @Path("/get-tenant")
    Uni<GetTenantResponse> getTenant(GetTenantInternalRequest request);

    default GetTenantResponse getTenant(long timeout, GetTenantInternalRequest request) {
        return getTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-tenant")
    Uni<SyncTenantResponse> syncTenant(SyncTenantInternalRequest request);

    default SyncTenantResponse syncTenant(long timeout, SyncTenantInternalRequest request) {
        return syncTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-tenant")
    Uni<Void> deleteTenant(DeleteTenantInternalRequest request);

    default void deleteTenant(long timeout, DeleteTenantInternalRequest request) {
        deleteTenant(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-tenant-permission")
    Uni<HasTenantPermissionResponse> hasTenantPermission(HasTenantPermissionInternalRequest request);

    default HasTenantPermissionResponse hasTenantPermission(long timeout, HasTenantPermissionInternalRequest request) {
        return hasTenantPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-tenant-permission")
    Uni<SyncTenantPermissionResponse> syncTenantPermission(SyncTenantPermissionInternalRequest request);

    default SyncTenantPermissionResponse syncTenantPermission(long timeout, SyncTenantPermissionInternalRequest request) {
        return syncTenantPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-project")
    Uni<GetProjectInternalResponse> getProject(GetProjectInternalRequest request);

    default GetProjectInternalResponse getProject(long timeout, GetProjectInternalRequest request) {
        return getProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-project")
    Uni<SyncProjectInternalResponse> syncProject(SyncProjectInternalRequest request);

    default SyncProjectInternalResponse syncProject(long timeout, SyncProjectInternalRequest request) {
        return syncProject(request).await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-project")
    Uni<Void> deleteProject(DeleteProjectInternalRequest request);

    default void deleteProject(long timeout, DeleteProjectInternalRequest request) {
        deleteProject(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-project-permission")
    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionInternalRequest request);

    default HasProjectPermissionInternalResponse hasProjectPermission(long timeout, HasProjectPermissionInternalRequest request) {
        return hasProjectPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-project-permission")
    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionInternalRequest request);

    default SyncProjectPermissionInternalResponse syncProjectPermission(long timeout, SyncProjectPermissionInternalRequest request) {
        return syncProjectPermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/get-stage")
    Uni<GetStageInternalResponse> getStage(GetStageInternalRequest request);

    default GetStageInternalResponse getStage(long timeout, GetStageInternalRequest request) {
        return getStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-stage")
    Uni<SyncStageInternalResponse> syncStage(SyncStageInternalRequest request);

    default SyncStageInternalResponse syncStage(long timeout, SyncStageInternalRequest request) {
        return syncStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/delete-stage")
    Uni<DeleteStageInternalResponse> deleteStage(DeleteStageInternalRequest request);

    default DeleteStageInternalResponse deleteStage(long timeout, DeleteStageInternalRequest request) {
        return deleteStage(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/has-stage-permission")
    Uni<HasStagePermissionInternalResponse> hasStagePermission(HasStagePermissionInternalRequest request);

    default HasStagePermissionInternalResponse hasStagePermission(long timeout, HasStagePermissionInternalRequest request) {
        return hasStagePermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    @PUT
    @Path("/sync-stage-permission")
    Uni<SyncStagePermissionInternalResponse> syncStagePermission(SyncStagePermissionInternalRequest request);

    default SyncStagePermissionInternalResponse syncStagePermission(long timeout, SyncStagePermissionInternalRequest request) {
        return syncStagePermission(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
