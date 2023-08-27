package com.omgservers.module.tenant.impl.service.projectShardedService;

import com.omgservers.dto.tenant.DeleteProjectShardedRequest;
import com.omgservers.dto.tenant.GetProjectShardedRequest;
import com.omgservers.dto.tenant.GetProjectInternalResponse;
import com.omgservers.dto.tenant.HasProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.HasProjectPermissionInternalResponse;
import com.omgservers.dto.tenant.SyncProjectShardedRequest;
import com.omgservers.dto.tenant.SyncProjectInternalResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionInternalResponse;
import io.smallrye.mutiny.Uni;

public interface ProjectShardedService {

    Uni<GetProjectInternalResponse> getProject(GetProjectShardedRequest request);

    Uni<SyncProjectInternalResponse> syncProject(SyncProjectShardedRequest request);

    Uni<Void> deleteProject(DeleteProjectShardedRequest request);

    Uni<HasProjectPermissionInternalResponse> hasProjectPermission(HasProjectPermissionShardedRequest request);

    Uni<SyncProjectPermissionInternalResponse> syncProjectPermission(SyncProjectPermissionShardedRequest request);
}
