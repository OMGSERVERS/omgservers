package com.omgservers.service.module.tenant.impl.service.projectService.testInterface;

import com.omgservers.model.dto.tenant.DeleteProjectPermissionRequest;
import com.omgservers.model.dto.tenant.DeleteProjectPermissionResponse;
import com.omgservers.model.dto.tenant.DeleteProjectRequest;
import com.omgservers.model.dto.tenant.DeleteProjectResponse;
import com.omgservers.model.dto.tenant.GetProjectRequest;
import com.omgservers.model.dto.tenant.GetProjectResponse;
import com.omgservers.model.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.model.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.model.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.model.dto.tenant.SyncProjectPermissionResponse;
import com.omgservers.model.dto.tenant.SyncProjectRequest;
import com.omgservers.model.dto.tenant.SyncProjectResponse;
import com.omgservers.model.dto.tenant.ViewProjectPermissionsRequest;
import com.omgservers.model.dto.tenant.ViewProjectPermissionsResponse;
import com.omgservers.model.dto.tenant.ViewProjectsRequest;
import com.omgservers.model.dto.tenant.ViewProjectsResponse;
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
