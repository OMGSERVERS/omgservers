package com.omgservers.module.tenant.impl.service.projectShardedService;

import com.omgservers.dto.tenant.DeleteProjectShardedRequest;
import com.omgservers.dto.tenant.GetProjectShardedRequest;
import com.omgservers.dto.tenant.GetProjectShardedResponse;
import com.omgservers.dto.tenant.HasProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.HasProjectPermissionShardedResponse;
import com.omgservers.dto.tenant.SyncProjectShardedRequest;
import com.omgservers.dto.tenant.SyncProjectShardedResponse;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedRequest;
import com.omgservers.dto.tenant.SyncProjectPermissionShardedResponse;
import io.smallrye.mutiny.Uni;

public interface ProjectShardedService {

    Uni<GetProjectShardedResponse> getProject(GetProjectShardedRequest request);

    Uni<SyncProjectShardedResponse> syncProject(SyncProjectShardedRequest request);

    Uni<Void> deleteProject(DeleteProjectShardedRequest request);

    Uni<HasProjectPermissionShardedResponse> hasProjectPermission(HasProjectPermissionShardedRequest request);

    Uni<SyncProjectPermissionShardedResponse> syncProjectPermission(SyncProjectPermissionShardedRequest request);
}
