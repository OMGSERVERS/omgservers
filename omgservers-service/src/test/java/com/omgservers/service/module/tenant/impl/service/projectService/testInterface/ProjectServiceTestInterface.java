package com.omgservers.service.module.tenant.impl.service.projectService.testInterface;

import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.DeleteTenantProjectPermissionResponse;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.DeleteTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.GetTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.VerifyTenantProjectPermissionExistsResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.SyncTenantProjectPermissionRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.SyncTenantProjectPermissionResponse;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectResponse;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.tenantProjectPermission.ViewTenantProjectPermissionsResponse;
import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsRequest;
import com.omgservers.schema.module.tenant.tenantProject.ViewTenantProjectsResponse;
import com.omgservers.service.module.tenant.impl.service.projectService.ProjectService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ProjectServiceTestInterface {
    private static final long TIMEOUT = 1L;

    final ProjectService projectService;

    public GetTenantProjectResponse getProject(GetTenantProjectRequest request) {
        return projectService.getProject(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantProjectResponse syncProject(SyncTenantProjectRequest request) {
        return projectService.syncProject(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantProjectsResponse viewProjects(ViewTenantProjectsRequest request) {
        return projectService.viewProjects(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantProjectResponse deleteProject(DeleteTenantProjectRequest request) {
        return projectService.deleteProject(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewTenantProjectPermissionsResponse viewProjectPermissions(ViewTenantProjectPermissionsRequest request) {
        return projectService.viewProjectPermissions(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public VerifyTenantProjectPermissionExistsResponse hasProjectPermission(VerifyTenantProjectPermissionExistsRequest request) {
        return projectService.hasProjectPermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncTenantProjectPermissionResponse syncProjectPermission(SyncTenantProjectPermissionRequest request) {
        return projectService.syncProjectPermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteTenantProjectPermissionResponse deleteProjectPermission(DeleteTenantProjectPermissionRequest request) {
        return projectService.deleteProjectPermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
