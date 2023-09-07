package com.omgservers.module.tenant.impl.service.projectService;

import com.omgservers.dto.tenant.DeleteProjectRequest;
import com.omgservers.dto.tenant.GetProjectRequest;
import com.omgservers.dto.tenant.GetProjectResponse;
import com.omgservers.dto.tenant.HasProjectPermissionRequest;
import com.omgservers.dto.tenant.HasProjectPermissionResponse;
import com.omgservers.dto.tenant.SyncProjectRequest;
import com.omgservers.dto.tenant.SyncProjectShardedResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionResponse;
import io.smallrye.mutiny.Uni;

public interface ProjectService {

    Uni<GetProjectResponse> getProject(GetProjectRequest request);

    Uni<SyncProjectShardedResponse> syncProject(SyncProjectRequest request);

    Uni<Void> deleteProject(DeleteProjectRequest request);

    Uni<HasProjectPermissionResponse> hasProjectPermission(HasProjectPermissionRequest request);

    Uni<SyncProjectPermissionResponse> syncProjectPermission(SyncProjectPermissionRequest request);
}
