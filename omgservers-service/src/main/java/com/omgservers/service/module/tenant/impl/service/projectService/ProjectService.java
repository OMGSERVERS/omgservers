package com.omgservers.service.module.tenant.impl.service.projectService;

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
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ProjectService {

    Uni<GetProjectResponse> getProject(@Valid GetProjectRequest request);

    Uni<SyncProjectResponse> syncProject(@Valid SyncProjectRequest request);

    Uni<ViewProjectsResponse> viewProjects(@Valid ViewProjectsRequest request);

    Uni<DeleteProjectResponse> deleteProject(@Valid DeleteProjectRequest request);

    Uni<ViewProjectPermissionsResponse> viewProjectPermissions(@Valid ViewProjectPermissionsRequest request);

    Uni<HasProjectPermissionResponse> hasProjectPermission(@Valid HasProjectPermissionRequest request);

    Uni<SyncProjectPermissionResponse> syncProjectPermission(@Valid SyncProjectPermissionRequest request);

    Uni<DeleteProjectPermissionResponse> deleteProjectPermission(@Valid DeleteProjectPermissionRequest request);

}
