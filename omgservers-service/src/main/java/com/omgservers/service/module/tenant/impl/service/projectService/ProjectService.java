package com.omgservers.service.module.tenant.impl.service.projectService;

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
import com.omgservers.model.dto.tenant.ViewProjectsRequest;
import com.omgservers.model.dto.tenant.ViewProjectsResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ProjectService {

    Uni<GetProjectResponse> getProject(@Valid GetProjectRequest request);

    Uni<SyncProjectResponse> syncProject(@Valid SyncProjectRequest request);

    Uni<ViewProjectsResponse> viewProjects(@Valid ViewProjectsRequest request);

    Uni<DeleteProjectResponse> deleteProject(@Valid DeleteProjectRequest request);

    Uni<HasProjectPermissionResponse> hasProjectPermission(@Valid HasProjectPermissionRequest request);

    Uni<SyncProjectPermissionResponse> syncProjectPermission(@Valid SyncProjectPermissionRequest request);
}
