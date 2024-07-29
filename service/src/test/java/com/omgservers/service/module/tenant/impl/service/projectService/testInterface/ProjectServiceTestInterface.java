package com.omgservers.service.module.tenant.impl.service.projectService.testInterface;

import com.omgservers.schema.module.tenant.DeleteProjectPermissionRequest;
import com.omgservers.schema.module.tenant.DeleteProjectPermissionResponse;
import com.omgservers.schema.module.tenant.DeleteProjectRequest;
import com.omgservers.schema.module.tenant.DeleteProjectResponse;
import com.omgservers.schema.module.tenant.GetProjectRequest;
import com.omgservers.schema.module.tenant.GetProjectResponse;
import com.omgservers.schema.module.tenant.HasProjectPermissionRequest;
import com.omgservers.schema.module.tenant.HasProjectPermissionResponse;
import com.omgservers.schema.module.tenant.SyncProjectPermissionRequest;
import com.omgservers.schema.module.tenant.SyncProjectPermissionResponse;
import com.omgservers.schema.module.tenant.SyncProjectRequest;
import com.omgservers.schema.module.tenant.SyncProjectResponse;
import com.omgservers.schema.module.tenant.ViewProjectPermissionsRequest;
import com.omgservers.schema.module.tenant.ViewProjectPermissionsResponse;
import com.omgservers.schema.module.tenant.ViewProjectsRequest;
import com.omgservers.schema.module.tenant.ViewProjectsResponse;
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

    public GetProjectResponse getProject(GetProjectRequest request) {
        return projectService.getProject(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncProjectResponse syncProject(SyncProjectRequest request) {
        return projectService.syncProject(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewProjectsResponse viewProjects(ViewProjectsRequest request) {
        return projectService.viewProjects(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteProjectResponse deleteProject(DeleteProjectRequest request) {
        return projectService.deleteProject(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public ViewProjectPermissionsResponse viewProjectPermissions(ViewProjectPermissionsRequest request) {
        return projectService.viewProjectPermissions(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public HasProjectPermissionResponse hasProjectPermission(HasProjectPermissionRequest request) {
        return projectService.hasProjectPermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public SyncProjectPermissionResponse syncProjectPermission(SyncProjectPermissionRequest request) {
        return projectService.syncProjectPermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }

    public DeleteProjectPermissionResponse deleteProjectPermission(DeleteProjectPermissionRequest request) {
        return projectService.deleteProjectPermission(request)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
