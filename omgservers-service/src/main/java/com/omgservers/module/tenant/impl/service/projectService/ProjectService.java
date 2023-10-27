package com.omgservers.module.tenant.impl.service.projectService;

import com.omgservers.dto.tenant.DeleteProjectRequest;
import com.omgservers.dto.tenant.GetProjectRequest;
import com.omgservers.dto.tenant.GetProjectResponse;
import com.omgservers.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.dto.tenant.SyncProjectRequest;
import com.omgservers.dto.tenant.SyncProjectResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface ProjectService {

    Uni<GetProjectResponse> getProject(@Valid GetProjectRequest request);

    Uni<SyncProjectResponse> syncProject(@Valid SyncProjectRequest request);

    Uni<Void> deleteProject(@Valid DeleteProjectRequest request);

    Uni<HasProjectPermissionResponse> hasProjectPermission(@Valid HasProjectPermissionRequest request);

    Uni<SyncProjectPermissionResponse> syncProjectPermission(@Valid SyncProjectPermissionRequest request);
}
